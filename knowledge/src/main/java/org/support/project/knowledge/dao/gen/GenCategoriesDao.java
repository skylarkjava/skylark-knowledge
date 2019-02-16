package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.CategoriesEntity;
import org.support.project.ormapping.dao.AbstractDao;
import org.support.project.ormapping.exception.ORMappingException;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.ormapping.common.DBUserPool;
import org.support.project.ormapping.common.IDGen;
import org.support.project.ormapping.config.ORMappingParameter;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.common.util.PropertyUtil;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.aop.Aspect;

/**
 * テンプレートのマスタ
 */
@DI(instance=Instance.Singleton)
public class GenCategoriesDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenCategoriesDao get() {
		return Container.getComp(GenCategoriesDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得)
	 */
	public List<CategoriesEntity> physicalSelectAll() {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/CategoriesDao/CategoriesDao_physical_select_all.sql");
		return executeQueryList(sql, CategoriesEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得)
	 */
	public CategoriesEntity physicalSelectOnKey(Integer categoryId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/CategoriesDao/CategoriesDao_physical_select_on_key.sql");
		return executeQuerySingle(sql, CategoriesEntity.class, categoryId);
	}
	/**
	 * 全て取得
	 */
	public List<CategoriesEntity> selectAll() {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/CategoriesDao/CategoriesDao_select_all.sql");
		return executeQueryList(sql, CategoriesEntity.class);
	}
	/**
	 * キーで1件取得
	 */
	public CategoriesEntity selectOnKey(Integer categoryId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/CategoriesDao/CategoriesDao_select_on_key.sql");
		return executeQuerySingle(sql, CategoriesEntity.class, categoryId);
	}
	/**
	 * 登録(データを生で操作/DBの採番機能のカラムも自分でセット)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public CategoriesEntity rawPhysicalInsert(CategoriesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/CategoriesDao/CategoriesDao_raw_insert.sql");
		executeUpdate(sql,
			entity.getCategoryId()
			, entity.getCategoryName()
			, entity.getCategoryIcon()
			, entity.getDescription()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
		if (ORMappingParameter.DRIVER_NAME_POSTGRESQL.equals(driverClass)) {
			String setValSql = "select setval('TEMPLATE_MASTERS_TYPE_ID_seq', (select max(TYPE_ID) from TEMPLATE_MASTERS));";
			executeQuerySingle(setValSql, Long.class);
		}
		return entity;
	}
	/**
	 * 登録(データを生で操作)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public CategoriesEntity physicalInsert(CategoriesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/CategoriesDao/CategoriesDao_insert.sql");
		Class<?> type = PropertyUtil.getPropertyType(entity, "categoryId");
		Object key = executeInsert(sql, type,
			entity.getCategoryName()
			, entity.getCategoryIcon()
			, entity.getDescription()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		PropertyUtil.setPropertyValue(entity, "categoryId", key);
		return entity;
	}
	/**
	 * 登録(登録ユーザを指定)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public CategoriesEntity insert(Integer user, CategoriesEntity entity) {
		entity.setInsertUser(user);
		entity.setInsertDatetime(new Timestamp(new java.util.Date().getTime()));
		entity.setUpdateUser(user);
		entity.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		entity.setDeleteFlag(0);
		return physicalInsert(entity);
	}
	/**
	 * 登録
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public CategoriesEntity insert(CategoriesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public CategoriesEntity physicalUpdate(CategoriesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/CategoriesDao/CategoriesDao_update.sql");
		executeUpdate(sql,
			entity.getCategoryName()
			, entity.getCategoryIcon()
			, entity.getDescription()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getCategoryId()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public CategoriesEntity update(Integer user, CategoriesEntity entity) {
		CategoriesEntity db = selectOnKey(entity.getCategoryId());
		entity.setInsertUser(db.getInsertUser());
		entity.setInsertDatetime(db.getInsertDatetime());
		entity.setDeleteFlag(db.getDeleteFlag());
		entity.setUpdateUser(user);
		entity.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		return physicalUpdate(entity);
	}
	/**
	 * 更新
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public CategoriesEntity update(CategoriesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public CategoriesEntity save(Integer user, CategoriesEntity entity) {
		CategoriesEntity db = selectOnKey(entity.getCategoryId());
		if (db == null) {
			return insert(user, entity);
		} else {
			return update(user, entity);
		}
	}
	/**
	 * 保存(存在しなければ登録、存在すれば更新)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public CategoriesEntity save(CategoriesEntity entity) {
		CategoriesEntity db = selectOnKey(entity.getCategoryId());
		if (db == null) {
			return insert(entity);
		} else {
			return update(entity);
		}
	}
	/**
	 * 削除(データを生で操作/物理削除)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(Integer categoryId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/CategoriesDao/CategoriesDao_delete.sql");
		executeUpdate(sql, categoryId);
	}
	/**
	 * 削除(データを生で操作/物理削除)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(CategoriesEntity entity) {
		physicalDelete(entity.getCategoryId());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Integer categoryId) {
		CategoriesEntity db = selectOnKey(categoryId);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer categoryId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, categoryId);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, CategoriesEntity entity) {
		delete(user, entity.getCategoryId());

	}
	/**
	 * 削除(論理削除があれば論理削除)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(CategoriesEntity entity) {
		delete(entity.getCategoryId());

	}
	/**
	 復元(論理削除されていたものを有効化)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Integer categoryId) {
		CategoriesEntity db = physicalSelectOnKey(categoryId);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer categoryId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, categoryId);
	}
	/**
	 * 復元(論理削除されていたものを有効化)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, CategoriesEntity entity) {
		activation(user, entity.getCategoryId());

	}
	/**
	 * 復元(論理削除されていたものを有効化)
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(CategoriesEntity entity) {
		activation(entity.getCategoryId());

	}
}
