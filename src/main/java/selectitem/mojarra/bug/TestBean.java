package selectitem.mojarra.bug;

import com.sun.faces.component.visit.FullVisitContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UISelectItem;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

@ViewScoped
@ManagedBean
public class TestBean implements Serializable {

    private final List<String> list = Arrays.asList("one", "two");
    private final List<String> repeatValues = new ArrayList<>(Arrays.asList("hello", "hello"));
    private final List<String> regularValues = new ArrayList<>(Arrays.asList("hello", "hello"));

    public void changeSelectItemValue(final String clientId, final String render, final String value) {
        FacesContext.getCurrentInstance().getViewRoot().visitTree(
                new FullVisitContext(FacesContext.getCurrentInstance()),
                (context, target) -> {
                    if (target.getClientId().equals(clientId)) {
                        if (target instanceof UISelectItem) {
                            ((UISelectItem) target).setItemLabel(value);
                            ((UISelectItem) target).setItemValue(value);
                            ((UISelectItem) target).setItemDisabled(false);
                            ((HtmlSelectOneMenu) target.getParent()).setValue(value);
                            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(render);
                        }
                        return VisitResult.COMPLETE;
                    }
                    return VisitResult.ACCEPT;
                });
    }

    public List<String> getList() {
        return list;
    }

    public List<String> getRepeatValues() {
        return repeatValues;
    }

    public List<String> getRegularValues() {
        return regularValues;
    }
}
