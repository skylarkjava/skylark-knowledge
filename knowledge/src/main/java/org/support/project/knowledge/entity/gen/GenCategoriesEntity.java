package org.support.project.knowledge.entity.gen;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.sql.Timestamp;



import org.support.project.common.bean.ValidateError;
import org.support.project.common.validate.Validator;
import org.support.project.common.validate.ValidatorFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

/**
 * テンプレートのマスタ
 */
@DI(instance=Instance.Prototype)
public class GenCategoriesEntity implements Serializable {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenCategoriesEntity get() {
		return Container.getComp(GenCategoriesEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public GenCategoriesEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param categoryId カテゴリID
	 */

	public GenCategoriesEntity(Integer categoryId) {
		super();
		this.categoryId = categoryId;
	}
	/** カテゴリID */
	private Integer categoryId;
	/** カテゴリ名 */
	private String categoryName;
	/** アイコン */
	private String categoryIcon;
	/** 説明 */
	private String description;
	/** 登録ユーザ */
	private Integer insertUser;
	/** 登録日時 */
	private Timestamp insertDatetime;
	/** 更新ユーザ */
	private Integer updateUser;
	/** 更新日時 */
	private Timestamp updateDatetime;
	/** 削除フラグ */
	private Integer deleteFlag;

	/**
	 * カテゴリID を取得する
	 */
	public Integer getCategoryId() {
		return this.categoryId;
	}
	/**
	 * カテゴリID を設定する
	 * @param categoryId カテゴリID
	 */
	public GenCategoriesEntity setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
		return this;
	}

	/**
	 * カテゴリ名 を取得する
	 */
	public String getCategoryName() {
		return this.categoryName;
	}
	/**
	 * カテゴリ名 を設定する
	 * @param categoryName カテゴリ名
	 */
	public GenCategoriesEntity setCategoryName(String categoryName) {
		this.categoryName = categoryName;
		return this;
	}

	/**
	 * アイコン を取得する
	 */
	public String getCategoryIcon() {
		return this.categoryIcon;
	}
	/**
	 * アイコン を設定する
	 * @param categoryIcon アイコン
	 */
	public GenCategoriesEntity setCategoryIcon(String categoryIcon) {
		this.categoryIcon = categoryIcon;
		return this;
	}

	/**
	 * 説明 を取得する
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * 説明 を設定する
	 * @param description 説明
	 */
	public GenCategoriesEntity setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * 登録ユーザ を取得する
	 */
	public Integer getInsertUser() {
		return this.insertUser;
	}
	/**
	 * 登録ユーザ を設定する
	 * @param insertUser 登録ユーザ
	 */
	public GenCategoriesEntity setInsertUser(Integer insertUser) {
		this.insertUser = insertUser;
		return this;
	}

	/**
	 * 登録日時 を取得する
	 */
	public Timestamp getInsertDatetime() {
		return this.insertDatetime;
	}
	/**
	 * 登録日時 を設定する
	 * @param insertDatetime 登録日時
	 */
	public GenCategoriesEntity setInsertDatetime(Timestamp insertDatetime) {
		this.insertDatetime = insertDatetime;
		return this;
	}

	/**
	 * 更新ユーザ を取得する
	 */
	public Integer getUpdateUser() {
		return this.updateUser;
	}
	/**
	 * 更新ユーザ を設定する
	 * @param updateUser 更新ユーザ
	 */
	public GenCategoriesEntity setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
		return this;
	}

	/**
	 * 更新日時 を取得する
	 */
	public Timestamp getUpdateDatetime() {
		return this.updateDatetime;
	}
	/**
	 * 更新日時 を設定する
	 * @param updateDatetime 更新日時
	 */
	public GenCategoriesEntity setUpdateDatetime(Timestamp updateDatetime) {
		this.updateDatetime = updateDatetime;
		return this;
	}

	/**
	 * 削除フラグ を取得する
	 */
	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}
	/**
	 * 削除フラグ を設定する
	 * @param deleteFlag 削除フラグ
	 */
	public GenCategoriesEntity setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
		return this;
	}

	/**
	 * キーの値を取得
	 */
	public Object[] getKeyValues() {
		Object[] keyValues = new Object[1];
		keyValues[0] = this.categoryId;
		return keyValues;
	}
	/**
	 * キーの値を設定
	 * @param categoryId カテゴリID
	 */
	public void setKeyValues(Integer categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * キーで比較
	 */
	public boolean equalsOnKey(GenCategoriesEntity entity) {
		Object[] keyValues1 = getKeyValues();
		Object[] keyValues2 = entity.getKeyValues();
		for (int i = 0; i < keyValues1.length; i++) {
			Object val1 = keyValues1[i];
			Object val2 = keyValues2[i];
			if (val1 == null && val2 != null) {
				return false;
			}
			if (val1 != null && val2 == null) {
				return false;
			}
			if (val1 != null && val2 != null) {
				if (!val1.equals(val2)) {
					return false;
				}
			}

		}
		return true;
	}
	/**
	 * ToString
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("categoryId = ").append(categoryId).append("\n");
		builder.append("categoryName = ").append(categoryName).append("\n");
		builder.append("categoryIcon = ").append(categoryIcon).append("\n");
		builder.append("description = ").append(description).append("\n");
		builder.append("insertUser = ").append(insertUser).append("\n");
		builder.append("insertDatetime = ").append(insertDatetime).append("\n");
		builder.append("updateUser = ").append(updateUser).append("\n");
		builder.append("updateDatetime = ").append(updateDatetime).append("\n");
		builder.append("deleteFlag = ").append(deleteFlag).append("\n");
		return builder.toString();
	}
	/**
	 * 表示用の名称を変換
	 */
	protected String convLabelName(String label) {
		return label;
	}
	/**
	 * validate
	 */
	public List<ValidateError> validate() {
		List<ValidateError> errors = new ArrayList<>();
		Validator validator;
		ValidateError error;
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.categoryId, convLabelName("Category Id"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(this.categoryName, convLabelName("Category Name"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.categoryName, convLabelName("Category Name"), 256);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.categoryIcon, convLabelName("Category Icon"), 64);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.description, convLabelName("Description"), 1024);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.insertUser, convLabelName("Insert User"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.updateUser, convLabelName("Update User"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.deleteFlag, convLabelName("Delete Flag"));
		if (error != null) {
			errors.add(error);
		}
		return errors;
	}
	/**
	 * validate
	 */
	public List<ValidateError> validate(Map<String, String> values) {
		List<ValidateError> errors = new ArrayList<>();
		Validator validator;
		ValidateError error;
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("categoryId"), convLabelName("Category Id"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(values.get("categoryName"), convLabelName("Category Name"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("categoryName"), convLabelName("Category Name"), 256);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("categoryIcon"), convLabelName("Category Icon"), 64);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("description"), convLabelName("Description"), 1024);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("insertUser"), convLabelName("Insert User"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("updateUser"), convLabelName("Update User"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("deleteFlag"), convLabelName("Delete Flag"));
		if (error != null) {
			errors.add(error);
		}
		return errors;
	}

}
