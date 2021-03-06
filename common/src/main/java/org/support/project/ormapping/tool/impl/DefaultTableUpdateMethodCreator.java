package org.support.project.ormapping.tool.impl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.ormapping.common.NameConvertor;
import org.support.project.ormapping.entity.ColumnDefinition;
import org.support.project.ormapping.tool.DaoGenConfig;

public class DefaultTableUpdateMethodCreator {
	/** ログ */
	private static Log log = LogFactory
			.getLog(DefaultTableUpdateMethodCreator.class);

	private CreatorHelper helper = new CreatorHelper();
	private NameConvertor nameConvertor = new NameConvertor();

	private DaoGenConfig config;
	private DefaultTableSQLCreator sqlCreator;

	public void writeUpdateMethod(DaoGenConfig config, PrintWriter pw) {
		this.config = config;
		this.sqlCreator = new DefaultTableSQLCreator(config);

		writePhysicalUpdate(pw);
		writeUpdateOnUser(pw);
		writeUpdate(pw);

	}

	private void writeUpdate(PrintWriter pw) {
		// コメント
		pw.println("\t/**");
		pw.println("\t * 更新");
		pw.println("\t */");

		pw.println("\t@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)");
		// メソッド定義
		pw.print("\tpublic ");
		pw.print(config.getEntityClassName());
		pw.print(" update(");
		pw.print(config.getEntityClassName());
		pw.println(" entity) {");
		if (StringUtils.isNotEmpty(config.getCommonInsertUserName()) 
				|| StringUtils.isNotEmpty(config.getCommonUpdateUserName())) {
			pw.println("\t\tDBUserPool pool = Container.getComp(DBUserPool.class);");
			pw.print("\t\t");
			pw.print(config.getCommonUseridType());
			pw.print(" userId = (");
			pw.print(config.getCommonUseridType());
			pw.println(") pool.getUser();");
			pw.println("\t\treturn update(userId, entity);");
		} else {
			pw.println("\t\treturn physicalUpdate(entity);");
		}
		pw.println("\t}");

	}

	private void writeUpdateOnUser(PrintWriter pw) {
		// コメント
		pw.println("\t/**");
		pw.println("\t * 更新(更新ユーザを指定) ");
		pw.println("\t */");

		pw.println("\t@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)");
		// メソッド定義
		pw.print("\tpublic ");
		pw.print(config.getEntityClassName());
		pw.print(" update(");
		pw.print(config.getCommonUseridType());
		pw.print(" user, ");
		pw.print(config.getEntityClassName());
		pw.println(" entity) {");

		// ユーザ名の列定義
		List<ColumnDefinition> columnDefinitions = config.getTableDefinition()
				.getColumns();

		if (StringUtils.isNotEmpty(config.getCommonInsertUserName())
				|| StringUtils.isNotEmpty(config.getCommonDeleteFlag())) {
			// 登録ユーザは、DBに格納されたままの値を使うので、DBの値を取得
			pw.print("\t\t");
			pw.print(config.getEntityClassName());
			pw.print(" db = selectOnKey(");
			Collection<ColumnDefinition> primaryKeys = config
					.getPrimaryKeys(columnDefinitions);
			int count = 0;
			for (ColumnDefinition columnDefinition : primaryKeys) {
				if (count > 0) {
					pw.print(", ");
				}
				pw.print("entity.");
				String feildName = nameConvertor
						.colmnNameToFeildName(columnDefinition.getColumn_name());
				pw.print(helper.feildNameToGetter(feildName));
				pw.print("()");
				count++;
			}
			pw.println(");");

			ColumnDefinition userColumn = null;
			ColumnDefinition datetimeColumn = null;

			for (ColumnDefinition columnDefinition : columnDefinitions) {
				if (columnDefinition.getColumn_name().toLowerCase()
						.equals(config.getCommonInsertUserName().toLowerCase())) {
					userColumn = columnDefinition;
				} else if (columnDefinition.getColumn_name().toLowerCase()
						.equals(config.getCommonInsertDateTime().toLowerCase())) {
					datetimeColumn = columnDefinition;
				}
			}
			if (userColumn != null) {
				// 登録ユーザをセット
				String feildName = nameConvertor
						.colmnNameToFeildName(userColumn.getColumn_name());
				pw.print("\t\tentity.");
				pw.print(helper.feildNameToSetter(feildName));
				pw.print("(db.");
				pw.print(helper.feildNameToGetter(feildName));
				pw.println("());");
			}
			if (datetimeColumn != null) {
				// 登録ユーザをセット
				String feildName = nameConvertor
						.colmnNameToFeildName(datetimeColumn.getColumn_name());
				pw.print("\t\tentity.");
				pw.print(helper.feildNameToSetter(feildName));
				pw.print("(db.");
				pw.print(helper.feildNameToGetter(feildName));
				pw.println("());");
			}
			if (StringUtils.isNotEmpty(config.getCommonDeleteFlag())) {
				//削除フラグをセット
				String feildName = nameConvertor
						.colmnNameToFeildName(config.getCommonDeleteFlag());
				pw.print("\t\tentity.");
				pw.print(helper.feildNameToSetter(feildName));
				pw.print("(db.");
				pw.print(helper.feildNameToGetter(feildName));
				pw.println("());");
			}
		}

		if (StringUtils.isNotEmpty(config.getCommonUpdateUserName())) {
			ColumnDefinition userColumn = null;
			ColumnDefinition datetimeColumn = null;

			for (ColumnDefinition columnDefinition : columnDefinitions) {
				if (columnDefinition.getColumn_name().toLowerCase()
						.equals(config.getCommonUpdateUserName().toLowerCase())) {
					userColumn = columnDefinition;
				} else if (columnDefinition.getColumn_name().toLowerCase()
						.equals(config.getCommonUpdateDateTime().toLowerCase())) {
					datetimeColumn = columnDefinition;
				}
			}
			if (userColumn != null) {
				// 更新ユーザをセット
				String feildName = nameConvertor
						.colmnNameToFeildName(userColumn.getColumn_name());
				pw.print("\t\tentity.");
				pw.print(helper.feildNameToSetter(feildName));
				pw.println("(user);");
			}
			if (datetimeColumn != null) {
				// 更新ユーザをセット
				String feildName = nameConvertor
						.colmnNameToFeildName(datetimeColumn.getColumn_name());
				pw.print("\t\tentity.");
				pw.print(helper.feildNameToSetter(feildName));
				pw.println("(new Timestamp(new java.util.Date().getTime()));");
			}
		}
		pw.println("\t\treturn physicalUpdate(entity);");

		pw.println("\t}");
	}

	private void writePhysicalUpdate(PrintWriter pw) {
		List<ColumnDefinition> columnDefinitions = config.getTableDefinition()
				.getColumns();
		Collection<ColumnDefinition> primaryKeys = config
				.getPrimaryKeys(columnDefinitions);

		//log.info(columnDefinitions.size() + "");
		//log.info(primaryKeys.size() + "");

		if (columnDefinitions.size() == primaryKeys.size()) {
			// 列にキーしか存在しないので、アップデートしてもしかたがない
			return;
		}

		// コメント
		pw.println("\t/**");
		pw.println("\t * 更新(データを生で操作) ");
		pw.println("\t */");

		pw.println("\t@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)");
		// メソッド定義
		pw.print("\tpublic ");
		pw.print(config.getEntityClassName());
		pw.print(" physicalUpdate(");
		pw.print(config.getEntityClassName());
		pw.println(" entity) {");

		// SQLの取得
		pw.print("\t\tString sql = SQLManager.getInstance().getSql(\"");
		pw.print(config.getSqlPackagePath());
		pw.print("/");
		pw.print(sqlCreator.getUpdateSqlFileName());
		pw.println("\");");

		// SQLの実行
		pw.print("\t\t");
		pw.println("executeUpdate(sql, ");

		List<String> primaryKeyName = new ArrayList<>();
		for (ColumnDefinition column : primaryKeys) {
			primaryKeyName.add(column.getColumn_name());
		}

		// 値セット
		int count = 0;
		for (ColumnDefinition column : columnDefinitions) {
			if (!primaryKeyName.contains(column.getColumn_name())) {
				pw.print("\t\t\t");
				if (count > 0) {
					pw.print(", ");
				}
				pw.print("entity.");
				String feildName = nameConvertor.colmnNameToFeildName(column
						.getColumn_name());
				pw.print(helper.feildNameToGetter(feildName));
				pw.println("()");
				count++;
			}
		}

		// キーをセット
		for (ColumnDefinition column : primaryKeys) {
			pw.print("\t\t\t");
			pw.print(", ");
			pw.print("entity.");
			String feildName = nameConvertor.colmnNameToFeildName(column
					.getColumn_name());
			pw.print(helper.feildNameToGetter(feildName));
			pw.println("()");
			count++;
		}
		pw.print("\t\t");
		pw.println(");");
		
		pw.print("\t\t");
		pw.println("return entity;");
		
		pw.println("\t}");
	}

}
