package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenCategoriesDao;
import org.support.project.knowledge.entity.CategoriesEntity;
import org.support.project.ormapping.common.SQLManager;

/**
 * テンプレートのマスタ
 */
@DI(instance=Instance.Singleton)
public class CategoriesDao extends GenCategoriesDao {

	public static final int TYPE_ID_KNOWLEDGE = -100;
	public static final int TYPE_ID_BOOKMARK = -99;

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static CategoriesDao get() {
		return Container.getComp(CategoriesDao.class);
	}

	/**
	 * ID
	 */
	private int currentId = 0;

	/**
	 * IDを採番
	 * ※コミットしなくても次のIDを採番する為、保存しなければ欠番になる
	 */
	public Integer getNextId() {
		String sql = "SELECT MAX(TYPE_ID) FROM TEMPLATE_MASTERS;";
		Integer integer = executeQuerySingle(sql, Integer.class);
		if (integer != null) {
			if (currentId < integer) {
				currentId = integer;
			}
		}
		currentId++;
		return currentId;
	}


	/**
	 * データをtruncateする
	 *
	 * @return void
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void truncate() {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/CategoriesDao/CategoriesDao_truncate.sql");
		executeUpdate(sql);
	}
	/**
	 * sequenceをリセットする
	 *
	 * @return void
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void resetSequence() {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/CategoriesDao/CategoriesDao_alter_sequence.sql");
		executeUpdate(sql);
	}
}
