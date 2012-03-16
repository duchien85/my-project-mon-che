package bigant.monche.view.components;

import bigant.monche.model.vo.PhotoStatusVo;
import bigant.monche.names.TypeImageNames;
import android.content.Context;
import android.widget.TabHost;

public class HandleTabhost extends TabHost{
	
	public static final int RANDOM_NEW = 0;
	public static final int RANDOM = 1;

	public HandleTabhost(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void setCurrentTab(int index) {
		// TODO Auto-generated method stub
		if(index == RANDOM)
			PhotoStatusVo.getInstance().type = TypeImageNames.RANDOM;
		if(index == RANDOM_NEW)
			PhotoStatusVo.getInstance().type = TypeImageNames.RANDOM_UNREAD;
		super.setCurrentTab(index);
		
	}
}
