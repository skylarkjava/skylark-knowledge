package org.support.project.knowledge.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.ItemChoicesDao;
import org.support.project.knowledge.dao.CategoriesDao;
import org.support.project.knowledge.entity.CategoriesEntity;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.exception.InvalidParamException;

@DI(instance=Instance.Singleton)
public class CategoryLogic {
	private static Log LOG = LogFactory.getLog(CategoryLogic.class);
	public static CategoryLogic get() {
		return Container.getComp(CategoryLogic.class);
	}

	public static final int ITEM_TYPE_TEXT = 0;
	public static final int ITEM_TYPE_TEXTAREA = 1;
	public static final int ITEM_TYPE_RADIO = 10;
	public static final int ITEM_TYPE_CHECKBOX = 11;

	public static final String ITEM_TYPE_TEXT_STRING = "text";
	public static final String ITEM_TYPE_TEXTAREA_STRING = "textarea";
	public static final String ITEM_TYPE_RADIO_STRING = "radio";
	public static final String ITEM_TYPE_CHECKBOX_STRING = "checkbox";

	/**
	 * 画面から取得した項目の種類のテキストを、DBに保存するInt値に変換
	 * @param type
	 * @return
	 */
	public int convType(String type) {
		if (ITEM_TYPE_TEXT_STRING.equals(type)) {
			return ITEM_TYPE_TEXT;
		} else if (ITEM_TYPE_TEXTAREA_STRING.equals(type)) {
			return ITEM_TYPE_TEXTAREA;
		} else if (ITEM_TYPE_RADIO_STRING.equals(type)) {
			return ITEM_TYPE_RADIO;
		} else if (ITEM_TYPE_CHECKBOX_STRING.equals(type)) {
			return ITEM_TYPE_CHECKBOX;
		}
		LOG.warn("Item type: " + type + " is undefined.");
		return -1;
	}
	/**
	 * DBの項目種類をテキストに変換
	 * @param type
	 * @return
	 */
	public String convType(int type) {
		if (ITEM_TYPE_TEXT == type) {
			return ITEM_TYPE_TEXT_STRING;
		} else if (ITEM_TYPE_TEXTAREA == type) {
			return ITEM_TYPE_TEXTAREA_STRING;
		} else if (ITEM_TYPE_RADIO == type) {
			return ITEM_TYPE_RADIO_STRING;
		} else if (ITEM_TYPE_CHECKBOX == type) {
			return ITEM_TYPE_CHECKBOX_STRING;
		}
		LOG.warn("Item type: " + type + " is undefined.");
		return "";
	}

	/**
	 * カテゴリを新規登録
	 * @param category
	 * @param loginedUser
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public CategoriesEntity addCategory(CategoriesEntity category, LoginedUser loginedUser) {
		CategoriesDao categoryDao = CategoriesDao.get();
		// カテゴリ保存
		category = categoryDao.insert(category);

		return category;
	}

	/**
	 * カテゴリを更新
	 * @param category
	 * @param loginedUser
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public CategoriesEntity updateCategory(CategoriesEntity category, LoginedUser loginedUser) throws InvalidParamException {
		CategoriesDao categoryDao = CategoriesDao.get();

		CategoriesEntity db = categoryDao.selectOnKey(category.getCategoryId());
		if (db == null) {
			MessageResult messageResult = new MessageResult();
			messageResult.setStatus(MessageStatus.Warning.getValue());
			messageResult.setCode(HttpStatus.SC_BAD_REQUEST);
			messageResult.setMessage("update data is not found");
			throw new InvalidParamException(messageResult);
		}

		// カテゴリ更新
		db.setCategoryName(category.getCategoryName());
		db.setCategoryIcon(category.getCategoryIcon());
		db.setDescription(category.getDescription());
		categoryDao.update(db);

		return db;
	}


	/**
	 * カテゴリの削除
	 * @param typeId
	 * @param loginedUser
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void deleteCategory(Integer typeId, LoginedUser loginedUser) {
		CategoriesDao categoryDao = CategoriesDao.get();

		CategoriesEntity entity = categoryDao.selectOnKey(typeId);
		if (entity != null) {
			categoryDao.delete(entity);
		}
	}

	/**
	 * カテゴリの情報を取得
	 * @param id
	 * @return
	 */
	public CategoriesEntity loadCategory(Integer id) {
		CategoriesDao categoryDao = CategoriesDao.get();
		CategoriesEntity entity = categoryDao.selectOnKey(id);

		return entity;
	}



}
