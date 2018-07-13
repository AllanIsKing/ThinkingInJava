package saaf.common.fmw.report.model.dao.readonly;

import org.springframework.stereotype.Component;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

import saaf.common.fmw.report.model.entities.readonly.ElementBean;

/**
 * @author CHENRONGXUAN
 * @version Time：2018年1月12日 上午9:36:05 类说明
 */
@Component
public class NewFastGeneraDAO_HI_RO extends DynamicViewObjectImpl<ElementBean> {
	public NewFastGeneraDAO_HI_RO() {
		super();
	}
}
