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
 * メール
 */
@DI(instance=Instance.Prototype)
public class GenMailsEntity implements Serializable {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenMailsEntity get() {
		return Container.getComp(GenMailsEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public GenMailsEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param mailId MAIL_ID
	 */

	public GenMailsEntity(String mailId) {
		super();
		this.mailId = mailId;
	}
	/** MAIL_ID */
	private String mailId;
	/** ステータス */
	private Integer status;
	/** 送信先 */
	private String toAddress;
	/** 送信先名 */
	private String toName;
	/** 送信元 */
	private String fromAddress;
	/** 送信元名 */
	private String fromName;
	/** タイトル */
	private String title;
	/** メッセージ */
	private String content;
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
	 * MAIL_ID を取得する
	 */
	public String getMailId() {
		return this.mailId;
	}
	/**
	 * MAIL_ID を設定する
	 * @param mailId MAIL_ID
	 */
	public GenMailsEntity setMailId(String mailId) {
		this.mailId = mailId;
		return this;
	}

	/**
	 * ステータス を取得する
	 */
	public Integer getStatus() {
		return this.status;
	}
	/**
	 * ステータス を設定する
	 * @param status ステータス
	 */
	public GenMailsEntity setStatus(Integer status) {
		this.status = status;
		return this;
	}

	/**
	 * 送信先 を取得する
	 */
	public String getToAddress() {
		return this.toAddress;
	}
	/**
	 * 送信先 を設定する
	 * @param toAddress 送信先
	 */
	public GenMailsEntity setToAddress(String toAddress) {
		this.toAddress = toAddress;
		return this;
	}

	/**
	 * 送信先名 を取得する
	 */
	public String getToName() {
		return this.toName;
	}
	/**
	 * 送信先名 を設定する
	 * @param toName 送信先名
	 */
	public GenMailsEntity setToName(String toName) {
		this.toName = toName;
		return this;
	}

	/**
	 * 送信元 を取得する
	 */
	public String getFromAddress() {
		return this.fromAddress;
	}
	/**
	 * 送信元 を設定する
	 * @param fromAddress 送信元
	 */
	public GenMailsEntity setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
		return this;
	}

	/**
	 * 送信元名 を取得する
	 */
	public String getFromName() {
		return this.fromName;
	}
	/**
	 * 送信元名 を設定する
	 * @param fromName 送信元名
	 */
	public GenMailsEntity setFromName(String fromName) {
		this.fromName = fromName;
		return this;
	}

	/**
	 * タイトル を取得する
	 */
	public String getTitle() {
		return this.title;
	}
	/**
	 * タイトル を設定する
	 * @param title タイトル
	 */
	public GenMailsEntity setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * メッセージ を取得する
	 */
	public String getContent() {
		return this.content;
	}
	/**
	 * メッセージ を設定する
	 * @param content メッセージ
	 */
	public GenMailsEntity setContent(String content) {
		this.content = content;
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
	public GenMailsEntity setRowId(String rowId) {
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
	public GenMailsEntity setInsertUser(Integer insertUser) {
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
	public GenMailsEntity setInsertDatetime(Timestamp insertDatetime) {
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
	public GenMailsEntity setUpdateUser(Integer updateUser) {
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
	public GenMailsEntity setUpdateDatetime(Timestamp updateDatetime) {
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
	public GenMailsEntity setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
		return this;
	}

	/**
	 * キーの値を取得 
	 */
	public Object[] getKeyValues() {
		Object[] keyValues = new Object[1];
		keyValues[0] = this.mailId;
		return keyValues;
	}
	/**
	 * キーの値を設定 
	 * @param mailId MAIL_ID
	 */
	public void setKeyValues(String mailId) {
		this.mailId = mailId;
	}
	/**
	 * キーで比較 
	 */
	public boolean equalsOnKey(GenMailsEntity entity) {
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
		builder.append("mailId = ").append(mailId).append("\n");
		builder.append("status = ").append(status).append("\n");
		builder.append("toAddress = ").append(toAddress).append("\n");
		builder.append("toName = ").append(toName).append("\n");
		builder.append("fromAddress = ").append(fromAddress).append("\n");
		builder.append("fromName = ").append(fromName).append("\n");
		builder.append("title = ").append(title).append("\n");
		builder.append("content = ").append(content).append("\n");
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
		error = validator.validate(this.mailId, convLabelName("Mail Id"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.mailId, convLabelName("Mail Id"), 64);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(this.status, convLabelName("Status"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.status, convLabelName("Status"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(this.toAddress, convLabelName("To Address"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.toAddress, convLabelName("To Address"), 256);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.toName, convLabelName("To Name"), 256);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.fromAddress, convLabelName("From Address"), 256);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.fromName, convLabelName("From Name"), 256);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(this.title, convLabelName("Title"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.title, convLabelName("Title"), 256);
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
		error = validator.validate(values.get("mailId"), convLabelName("Mail Id"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("mailId"), convLabelName("Mail Id"), 64);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(values.get("status"), convLabelName("Status"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("status"), convLabelName("Status"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(values.get("toAddress"), convLabelName("To Address"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("toAddress"), convLabelName("To Address"), 256);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("toName"), convLabelName("To Name"), 256);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("fromAddress"), convLabelName("From Address"), 256);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("fromName"), convLabelName("From Name"), 256);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(values.get("title"), convLabelName("Title"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("title"), convLabelName("Title"), 256);
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
