package org.support.project.knowledge.entity;

import org.support.project.common.config.Resources;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenKnowledgesEntity;
import org.support.project.web.util.ThreadResources;


/**
 * ナレッジ
 */
@DI(instance=Instance.Prototype)
public class KnowledgesEntity extends GenKnowledgesEntity {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static KnowledgesEntity get() {
		return Container.getComp(KnowledgesEntity.class);
	}

	/**
	 * 登録者の名称
	 */
	private String insertUserName;
	/**
	 * 更新者の名称
	 */
	private String updateUserName;

	/** スコア(検索した際のスコア) */
	private Float score;

	/**
	 * コンストラクタ
	 */
	public KnowledgesEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param knowledgeId ナレッジID
	 */

	public KnowledgesEntity(Long knowledgeId) {
		super( knowledgeId);
	}

	/* (non-Javadoc)
	 * @see org.support.project.knowledge.entity.gen.GenKnowledgesEntity#getTitle()
	 */
	@Override
	public String getTitle() {
		if (StringUtils.isEmpty(super.getTitle())) {
			return super.getTitle();
		}
//		try {
//			return Control.doSamy(super.getTitle());
//		} catch (PolicyException | ScanException e) {
//			throw new SystemException(e);
//		}
		return super.getTitle();
	}

	/* (non-Javadoc)
	 * @see org.support.project.knowledge.entity.gen.GenKnowledgesEntity#getContent()
	 */
	@Override
	public String getContent() {
		if (StringUtils.isEmpty(super.getContent())) {
			return super.getContent();
		}
//		try {
//			return Control.doSamy(super.getContent());
//		} catch (PolicyException | ScanException e) {
//			throw new SystemException(e);
//		}
		return super.getContent();
	}

	/**
	 * @return the insertUserName
	 */
	public String getInsertUserName() {
		return insertUserName;
	}

	/**
	 * @param insertUserName the insertUserName to set
	 */
	public void setInsertUserName(String insertUserName) {
		this.insertUserName = insertUserName;
	}

	/**
	 * @return the updateUserName
	 */
	public String getUpdateUserName() {
		return updateUserName;
	}

	/**
	 * @param updateUserName the updateUserName to set
	 */
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	/**
	 * @return the score
	 */
	public Float getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Float score) {
		this.score = score;
	}


	/**
	 * 表示用の名称を変換
	 */
	@Override
	protected String convLabelName(String label) {
		Resources resources = ThreadResources.get().getResources();
		if ("Category".equals(label)) {
			String resource = resources.getResource("knowledge.add.label.category");
			return resource;
		} else if ("Title".equals(label)) {
			String resource = resources.getResource("knowledge.add.label.title");
			return resource;
		}
		return label;
	}



}
