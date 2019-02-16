package org.support.project.knowledge.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.config.Resources;
import org.support.project.common.validate.Validator;
import org.support.project.common.validate.ValidatorFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.gen.GenCategoriesEntity;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.util.ThreadResources;


/**
 * テンプレートのマスタ
 */
@DI(instance=Instance.Prototype)
public class CategoriesEntity extends GenCategoriesEntity {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;


	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static CategoriesEntity get() {
		return Container.getComp(CategoriesEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public CategoriesEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param categoryId カテゴリID
	 */

	public CategoriesEntity(Integer categoryId) {
		super( categoryId);
	}

	/**
	 * 表示用の名称を変換
	 */
	@Override
	protected String convLabelName(String label) {
		Resources resources = ThreadResources.get().getResources();
		if ("Category Name".equals(label)) {
			String resource = resources.getResource("knowledge.category.label.name");
			return resource;
		} else if ("Category Icon".equals(label)) {
			String resource = resources.getResource("knowledge.category.label.icon");
			return resource;
		} else if ("Description".equals(label)) {
			String resource = resources.getResource("knowledge.category.label.description");
			return resource;
		}
		return label;
	}


}
