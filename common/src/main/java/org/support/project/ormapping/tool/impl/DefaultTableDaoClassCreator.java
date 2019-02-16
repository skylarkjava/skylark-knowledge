package org.support.project.ormapping.tool.impl;

import java.io.PrintWriter;
import java.sql.Types;
import java.util.Collection;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.ormapping.common.NameConvertor;
import org.support.project.ormapping.entity.ColumnDefinition;
import org.support.project.ormapping.tool.DaoGenConfig;

public class DefaultTableDaoClassCreator {
	/** ログ */
	private static Log log = LogFactory.getLog(DefaultTableDaoClassCreator.class);

	private CreatorHelper helper = new CreatorHelper();
	private NameConvertor nameConvertor = new NameConvertor();
	
	private DaoGenConfig config;
	
	public DefaultTableDaoClassCreator(
			DaoGenConfig config) {
		super();
		this.config = config;
	}

	public void create() {
		createGenDao();
		createDao();
	}
	
	
	
	private void createDao() {
		if (config.getDaoFile().exists()) {
			log.info(config.getDaoFile().getAbsolutePath() + "は既に存在するため、作成処理をスキップします");
			return;
		}
		log.info(config.getDaoFile().getAbsolutePath() + "を作成します");
		
		PrintWriter pw = null;
		try {
			pw = helper.getPrintWriter(config.getDaoFile());
			
			pw.println("package " + config.getDaoPackageName() + ";");
			pw.println();
			pw.println("import org.support.project.di.Container;");
			pw.println("import org.support.project.di.DI;");
			pw.println("import org.support.project.di.Instance;");
			pw.println();
			
			pw.println("import " + config.getGenPackage() + "." + config.getGenDaoClassName() + ";");
			pw.println();
			
			pw.println("/**");
			pw.println(" * " + config.getTableDefinition().getRemarks());
			pw.println(" */");

			pw.println("@DI(instance=Instance.Singleton)");
			pw.println("public class " + config.getDaoClassName() + " extends " + config.getGenDaoClassName() + " {");
			pw.println();
			pw.println("\t/** SerialVersion */");
			pw.println("\tprivate static final long serialVersionUID = 1L;");
			
			//インスタンス取得
			pw.println(helper.makeInstanceMethod(config.getDaoClassName()));
			
			//プライマリキーが1つ、かつintであればID取得のメソッド生成
			Collection<ColumnDefinition> keys = config.getPrimaryKeys();
			if (keys.size() == 1) {
				ColumnDefinition key = keys.iterator().next();
				if (key.getData_type() == Types.INTEGER) {
					pw.println();
					pw.println("\t/**");
					pw.println("\t * ID ");
					pw.println("\t */");
					pw.println("\tprivate int currentId = 0;");
					pw.println();
					
					pw.println("\t/**");
					pw.println("\t * IDを採番 ");
					pw.println("\t * ※コミットしなくても次のIDを採番する為、保存しなければ欠番になる ");
					pw.println("\t */");
					
					pw.println("\tpublic Integer getNextId() {");
					pw.print("\t\tString sql = \"SELECT MAX(");
					pw.print(key.getColumn_name());
					pw.print(") FROM ");
					pw.print(config.getTableDefinition().getTable_name());
					pw.println(";\";");
					pw.println("\t\tInteger integer = executeQuerySingle(sql, Integer.class);");
					pw.println("\t\tif (integer != null) {");
					pw.println("\t\t\tif (currentId < integer) {");
					pw.println("\t\t\t\tcurrentId = integer;");
					pw.println("\t\t\t}");
					pw.println("\t\t}");
					pw.println("\t\tcurrentId++;");
					pw.println("\t\treturn currentId;");
					pw.println("\t}");
				}
			}
			
			pw.println();
			pw.println();
			pw.println("}");
			
			pw.flush();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
		
	}
//
//
//
//
	private void createGenDao() {
		log.info(config.getGenDaoFile().getAbsolutePath() + "を作成します");
		
		PrintWriter pw = null;
		try {
			List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
			
			pw = helper.getPrintWriter(config.getGenDaoFile());
			
			//クラス定義部分の出力
			pw.println("package " + config.getGenPackage() + ";");
			pw.println();
			pw.println("import java.util.List;");
//			pw.println("import java.util.Date;");
//			pw.println("import java.sql.Timestamp;");
			pw.println();
			pw.println(helper.getColmnTypeImport(columnDefinitions));
			pw.println();
			pw.println("import " + config.getEntityPackageName() + "." + config.getEntityClassName() + ";");
			pw.println("import org.support.project.ormapping.dao.AbstractDao;");
			pw.println("import org.support.project.ormapping.exception.ORMappingException;");
			pw.println("import org.support.project.ormapping.common.SQLManager;");
			pw.println("import org.support.project.ormapping.common.DBUserPool;");
//			pw.println("import org.support.project.ormapping.exception.ORMappingException;");
			pw.println("import org.support.project.ormapping.common.IDGen;");
			pw.println("import org.support.project.ormapping.config.ORMappingParameter;");
			pw.println("import org.support.project.ormapping.connection.ConnectionManager;");
			
			pw.println("import org.support.project.common.util.PropertyUtil;");
			pw.println();
			pw.println("import org.support.project.di.Container;");
			pw.println("import org.support.project.di.DI;");
			pw.println("import org.support.project.di.Instance;");
			pw.println("import org.support.project.aop.Aspect;");
			pw.println();
			
			
			
			
			pw.println("/**");
			pw.println(" * " + config.getTableDefinition().getRemarks());
			pw.println(" */");
			
			pw.println("@DI(instance=Instance.Singleton)");
			pw.println("public class " + config.getGenDaoClassName() + " extends AbstractDao {");
			pw.println();
			
			pw.println("\t/** SerialVersion */");
			pw.println("\tprivate static final long serialVersionUID = 1L;");
			pw.println();
			
			//各メソッドの出力
			
			//インスタンス取得
			pw.println(helper.makeInstanceMethod(config.getGenDaoClassName()));
			
			//select系のメソッド出力
			DefaultTableSelectMethodCreator selectMethodCreator = new DefaultTableSelectMethodCreator();
			selectMethodCreator.writeSelectMethod(config, pw);
			
			//insert系のメソッド出力
			DefaultTableInsertMethodCreator insertMethodCreator = new DefaultTableInsertMethodCreator();
			insertMethodCreator.writeInsertMethod(config, pw);
			
			//update系のメソッド出力
			DefaultTableUpdateMethodCreator updateMethodCreator = new DefaultTableUpdateMethodCreator();
			updateMethodCreator.writeUpdateMethod(config, pw);
			
			//save系メソッド出力
			DefaultTableSaveMethodCreator saveMethodCreator = new DefaultTableSaveMethodCreator();
			saveMethodCreator.writeSaveMethod(config, pw);
			
			//delete系メソッド出力
			DefaultTableDeleteMethodCreator deleteMethodCreator = new DefaultTableDeleteMethodCreator();
			deleteMethodCreator.writedeleteMethod(config, pw);
			
			pw.println();
			pw.println("}");
			
			pw.flush();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	
}
