package vn.cahao.hiepkhachhanh;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import vn.hongcuu.hongcuu.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class DocListEpubTrongMayActivity extends SherlockFragmentActivity {

	private DocListEpubTrongMayApdapter apdapter;
	private DtoListEpub dto;
	private ArrayList<DtoListEpub> dataepub = new ArrayList<DtoListEpub>();
	// layout
	private ListView ldata;
	File rootDir = Environment.getExternalStorageDirectory();

	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);
		setContentView(R.layout.doclistepubtrongmay_activity);
		ldata = (ListView) findViewById(R.id.lv_data);
		getFileSDCard();
	}

	private void getFileSDCard() {
		new LoadVideoFromSdcard().execute();
		
	}

	private class LoadVideoFromSdcard extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... params) {
			epubList(rootDir);
			return null;
		}

	}
	//=======================
	
	private List<File> epubList(File dir) {
		List<File> res = new ArrayList<File>();
		if (dir.isDirectory()) {
			File[] f = dir.listFiles();
			if (f != null) {
				for (int i = 0; i < f.length; i++) {
					if (f[i].isDirectory()) {
						res.addAll(epubList(f[i]));
					} else {
						String lowerCasedName = f[i].getName().toLowerCase();
						if (lowerCasedName.endsWith(".epub")) {
	
							
							dto=new DtoListEpub();
				        	dto.setPathEpub(f[i].toString());
				        	dto.setNameEpub(f[i].getName().replace(".epub", ""));
				        	Log.d("capt-dto.getPathEpub()->", ""+dto.getPathEpub());
				        	Log.d("capt-dto.getNameEpub()->", ""+dto.getNameEpub());
				        	dataepub.add(dto);
							//res.add(f[i]);
						}
					}
				}
			}
		}
		 runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					setListData(dataepub);
				}
			});
		
		return res;
	}
	//=======================
	
	private void setListData(ArrayList<DtoListEpub> dataepub) {
		apdapter = new DocListEpubTrongMayApdapter(this, dataepub);
		ldata.setAdapter(apdapter);
		
	}

}
