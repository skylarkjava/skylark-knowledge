package org.support.project.web.entity.gen;

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
 * ロケール
 */
@DI(instance=Instance.Prototype)
public class GenLocalesEntity implements Serializable {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenLocalesEntity get() {
		return Container.getComp(GenLocalesEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public GenLocalesEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param key キー
	 */

	public GenLocalesEntity(String key) {
		super();
		this.key = key;
	}
	/** キー */
	private String key;
	/** 言語 */
	private String language;
	/** 国 */
	private String country;
	/** バリアント */
	private String variant;
	/** 表示名 */
	private String dispName;
	/** 国旗のアイコン */
	private String flagIcon;
	/** 行ID */
	private String rowId;
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
	 * キー を取得する
	 */
	public String getKey() {
		return this.key;
	}
	/**
	 * キー を設定する
	 * @param key キー
	 */
	public GenLocalesEntity setKey(String key) {
		this.key = key;
		return this;
	}

	/**
	 * 言語 を取得する
	 */
	public String getLanguage() {
		return this.language;
	}
	/**
	 * 言語 を設定する
	 * @param language 言語
	 */
	public GenLocalesEntity setLanguage(String language) {
		this.language = language;
		return this;
	}

	/**
	 * 国 を取得する
	 */
	public String getCountry() {
		return this.country;
	}
	/**
	 * 国 を設定する
	 * @param country 国
	 */
	public GenLocalesEntity setCountry(String country) {
		this.country = country;
		return this;
	}

	/**
	 * バリアント を取得する
	 */
	public String getVariant() {
		return this.variant;
	}
	/**
	 * バリアント を設定する
	 * @param variant バリアント
	 */
	public GenLocalesEntity setVariant(String variant) {
		this.variant = variant;
		return this;
	}

	/**
	 * 表示名 を取得する
	 */
	public String getDispName() {
		return this.dispName;
	}
	/**
	 * 表示名 を設定する
	 * @param dispName 表示名
	 */
	public GenLocalesEntity setDispName(String dispName) {
		this.dispName = dispName;
		return this;
	}

	/**
	 * 国旗のアイコン を取得する
	 */
	public String getFlagIcon() {
		return this.flagIcon;
	}
	/**
	 * 国旗のアイコン を設定する
	 * @param flagIcon 国旗のアイコン
	 */
	public GenLocalesEntity setFlagIcon(String flagIcon) {
		this.flagIcon = flagIcon;
		return this;
	}

	/**
	 * 行ID を取得する
	 */
	public String getRowId() {
		return this.rowId;
	}
	/**
	 * 行ID を設定する
	 * @param rowId 行ID
	 */
	public GenLocalesEntity setRowId(String rowId) {
		this.rowId = rowId;
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
	public GenLocalesEntity setInsertUser(Integer insertUser) {
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
	public GenLocalesEntity setInsertDatetime(Timestamp insertDatetime) {
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
	public GenLocalesEntity setUpdateUser(Integer updateUser) {
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
	public GenLocalesEntity setUpdateDatetime(Timestamp updateDatetime) {
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
	public GenLocalesEntity setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
		return this;
	}

	/**
	 * キーの値を取得 
	 */
	public Object[] getKeyValues() {
		Object[] keyValues = new Object[1];
		keyValues[0] = this.key;
		return keyValues;
	}
	/**
	 * キーの値を設定 
	 * @param key キー
	 */
	public void setKeyValues(String key) {
		this.key = key;
	}
	/**
	 * キーで比較 
	 */
	public boolean equalsOnKey(GenLocalesEntity entity) {
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
		builder.append("key = ").append(key).append("\n");
		builder.append("language = ").append(language).append("\n");
		builder.append("country = ").append(country).append("\n");
		builder.append("variant = ").append(variant).append("\n");
		builder.append("dispName = ").append(dispName).append("\n");
		builder.append("flagIcon = ").append(flagIcon).append("\n");
		builder.append("rowId = ").append(rowId).append("\n");
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
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(this.key, convLabelName("Key"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.key, convLabelName("Key"), 12);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(this.language, convLabelName("Language"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.language, convLabelName("Language"), 4);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.country, convLabelName("Country"), 4);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.variant, convLabelName("Variant"), 4);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.dispName, convLabelName("Disp Name"), 128);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.flagIcon, convLabelName("Flag Icon"), 24);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.rowId, convLabelName("Row Id"), 64);
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
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(values.get("key"), convLabelName("Key"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("key"), convLabelName("Key"), 12);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(values.get("language"), convLabelName("Language"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("language"), convLabelName("Language"), 4);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("country"), convLabelName("Country"), 4);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("variant"), convLabelName("Variant"), 4);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("dispName"), convLabelName("Disp Name"), 128);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("flagIcon"), convLabelName("Flag Icon"), 24);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("rowId"), convLabelName("Row Id"), 64);
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
