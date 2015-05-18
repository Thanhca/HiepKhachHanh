package vn.cahao.hiepkhachhanh;

import java.util.List;
import vn.hongcuu.hongcuu.R;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DocListEpubTrongMayApdapter extends ArrayAdapter<DtoListEpub> {

	
	private static class ViewHolder {
		public final RelativeLayout rl_item_list;
		public final TextView tvNameEpub;
	
		private ViewHolder(RelativeLayout rl_item_list, TextView tvNameEpub) {
			this.rl_item_list = rl_item_list;
			this.tvNameEpub = tvNameEpub;
		}
	
		public static ViewHolder create(RelativeLayout rl_item_list) {
			TextView tvNameEpub = (TextView)rl_item_list.findViewById( R.id.tv_nameEpub );
			return new ViewHolder( rl_item_list, tvNameEpub );
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder vh;
		if ( convertView == null ) {
			View view = mInflater.inflate( R.layout.doclistepubtrongmay_item, parent, false );
			vh = ViewHolder.create( (RelativeLayout)view );
			view.setTag( vh );
		} else {
			vh = (ViewHolder)convertView.getTag();
		}

		final DtoListEpub item = getItem( position );

		vh.tvNameEpub.setText(item.getNameEpub());
		 vh.rl_item_list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getContext(), OpenEpubActivity.class);
				i.putExtra("bpath", item.getPathEpub());
				getContext().startActivity(i);
				
				
				
			}
		});
		return vh.rl_item_list;
	}

	private LayoutInflater mInflater;

	// Constructors
	public DocListEpubTrongMayApdapter(Context context, List<DtoListEpub> objects) {
		super(context, 0, objects);
		this.mInflater = LayoutInflater.from( context );
	}
	public DocListEpubTrongMayApdapter(Context context, DtoListEpub[] objects) {
		super(context, 0, objects);
		this.mInflater = LayoutInflater.from( context );
	}
}

