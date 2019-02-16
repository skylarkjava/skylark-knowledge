package org.support.project.knowledge.entity.gen;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.io.InputStream;
import java.sql.Timestamp;



import org.support.project.common.bean.ValidateError;
import org.support.project.common.validate.Validator;
import org.support.project.common.validate.ValidatorFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

/**
 * 添付ファイル
 */
@DI(instance=Instance.Prototype)
public class GenKnowledgeFilesEntity implements Serializable {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenKnowledgeFilesEntity get() {
		return Container.getComp(GenKnowledgeFilesEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public GenKnowledgeFilesEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param fileNo 添付ファイル番号
	 */

	public GenKnowledgeFilesEntity(Long fileNo) {
		super();
		this.fileNo = fileNo;
	}
	/** 添付ファイル番号 */
	private Long fileNo;
	/** ナレッジID */
	private Long knowledgeId;
	/** コメント番号 */
	private Long commentNo;
	/** ファイル名 */
	private String fileName;
	/** ファイルサイズ */
	private Double fileSize;
	/** バイナリ */
	private InputStream fileBinary;
	/** パース結果 */
	private Integer parseStatus;
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
	 * 添付ファイル番号 を取得する
	 */
	public Long getFileNo() {
		return this.fileNo;
	}
	/**
	 * 添付ファイル番号 を設定する
	 * @param fileNo 添付ファイル番号
	 */
	public GenKnowledgeFilesEntity setFileNo(Long fileNo) {
		this.fileNo = fileNo;
		return this;
	}

	/**
	 * ナレッジID を取得する
	 */
	public Long getKnowledgeId() {
		return this.knowledgeId;
	}
	/**
	 * ナレッジID を設定する
	 * @param knowledgeId ナレッジID
	 */
	public GenKnowledgeFilesEntity setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
		return this;
	}

	/**
	 * コメント番号 を取得する
	 */
	public Long getCommentNo() {
		return this.commentNo;
	}
	/**
	 * コメント番号 を設定する
	 * @param commentNo コメント番号
	 */
	public GenKnowledgeFilesEntity setCommentNo(Long commentNo) {
		this.commentNo = commentNo;
		return this;
	}

	/**
	 * ファイル名 を取得する
	 */
	public String getFileName() {
		return this.fileName;
	}
	/**
	 * ファイル名 を設定する
	 * @param fileName ファイル名
	 */
	public GenKnowledgeFilesEntity setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	/**
	 * ファイルサイズ を取得する
	 */
	public Double getFileSize() {
		return this.fileSize;
	}
	/**
	 * ファイルサイズ を設定する
	 * @param fileSize ファイルサイズ
	 */
	public GenKnowledgeFilesEntity setFileSize(Double fileSize) {
		this.fileSize = fileSize;
		return this;
	}

	/**
	 * バイナリ を取得する
	 */
	public InputStream getFileBinary() {
		return this.fileBinary;
	}
	/**
	 * バイナリ を設定する
	 * @param fileBinary バイナリ
	 */
	public GenKnowledgeFilesEntity setFileBinary(InputStream fileBinary) {
		this.fileBinary = fileBinary;
		return this;
	}

	/**
	 * パース結果 を取得する
	 */
	public Integer getParseStatus() {
		return this.parseStatus;
	}
	/**
	 * パース結果 を設定する
	 * @param parseStatus パース結果
	 */
	public GenKnowledgeFilesEntity setParseStatus(Integer parseStatus) {
		this.parseStatus = parseStatus;
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
	public GenKnowledgeFilesEntity setInsertUser(Integer insertUser) {
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
	public GenKnowledgeFilesEntity setInsertDatetime(Timestamp insertDatetime) {
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
	public GenKnowledgeFilesEntity setUpdateUser(Integer updateUser) {
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
	public GenKnowledgeFilesEntity setUpdateDatetime(Timestamp updateDatetime) {
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
	public GenKnowledgeFilesEntity setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
		return this;
	}

	/**
	 * キーの値を取得 
	 */
	public Object[] getKeyValues() {
		Object[] keyValues = new Object[1];
		keyValues[0] = this.fileNo;
		return keyValues;
	}
	/**
	 * キーの値を設定 
	 * @param fileNo 添付ファイル番号
	 */
	public void setKeyValues(Long fileNo) {
		this.fileNo = fileNo;
	}
	/**
	 * キーで比較 
	 */
	public boolean equalsOnKey(GenKnowledgeFilesEntity entity) {
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
		builder.append("fileNo = ").append(fileNo).append("\n");
		builder.append("knowledgeId = ").append(knowledgeId).append("\n");
		builder.append("commentNo = ").append(commentNo).append("\n");
		builder.append("fileName = ").append(fileName).append("\n");
		builder.append("fileSize = ").append(fileSize).append("\n");
		builder.append("fileBinary = ").append(fileBinary).append("\n");
		builder.append("parseStatus = ").append(parseStatus).append("\n");
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
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(this.fileName, convLabelName("File Name"), 256);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(this.parseStatus, convLabelName("Parse Status"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(this.parseStatus, convLabelName("Parse Status"));
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
		validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);
		error = validator.validate(values.get("fileName"), convLabelName("File Name"), 256);
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.REQUIRED);
		error = validator.validate(values.get("parseStatus"), convLabelName("Parse Status"));
		if (error != null) {
			errors.add(error);
		}
		validator = ValidatorFactory.getInstance(Validator.INTEGER);
		error = validator.validate(values.get("parseStatus"), convLabelName("Parse Status"));
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
