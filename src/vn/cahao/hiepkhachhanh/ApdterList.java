package vn.cahao.hiepkhachhanh;

import java.util.List;
import vn.hongcuu.hongcuu.R;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ApdterList extends ArrayAdapter<DotoListApp> {

	
	private static class ViewHolder {
		public final RelativeLayout rl_lst_app;
		public final TextView tvName;
	
		private ViewHolder(RelativeLayout rl_lst_app, TextView tvName) {
			this.rl_lst_app = rl_lst_app;
			this.tvName = tvName;
		}
	
		public static ViewHolder create(RelativeLayout rl_lst_app) {
			TextView tvName = (TextView)rl_lst_app.findViewById( R.id.tv_name );
			return new ViewHolder( rl_lst_app, tvName );
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder vh;
		if ( convertView == null ) {
			View view = mInflater.inflate( R.layout.item_list, parent, false );
			vh = ViewHolder.create( (RelativeLayout)view );
			view.setTag( vh );
		} else {
			vh = (ViewHolder)convertView.getTag();
		}

		final DotoListApp item = getItem( position );
		vh.tvName.setText(""+item.getNameTruyen());
		
	    vh.rl_lst_app.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String url1="https://play.google.com/store/apps/details?id=com.hipstoreggp.hipstoremobi"+item.getFileApk();
				Intent launchIntent =getContext().getPackageManager().getLaunchIntentForPackage("com.android.vending");
			    ComponentName comp = new ComponentName("com.android.vending", "com.google.android.finsky.activities.LaunchUrlHandlerActivity"); 
			    launchIntent.setComponent(comp);
			    launchIntent.setData(Uri.parse(url1));
			    getContext().startActivity(launchIntent);
				
			}
		});

		return vh.rl_lst_app;
	}

	private LayoutInflater mInflater;

	// Constructors
	public ApdterList(Context context, List<DotoListApp> objects) {
		super(context, 0, objects);
		this.mInflater = LayoutInflater.from( context );
	}
	public ApdterList(Context context, DotoListApp[] objects) {
		super(context, 0, objects);
		this.mInflater = LayoutInflater.from( context );
	}
}
