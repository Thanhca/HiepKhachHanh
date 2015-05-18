
package vn.cahao.hiepkhachhanh;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import vn.cahao.hiepkhachhanh.util.Common;
import vn.hongcuu.hongcuu.R;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FileChooser extends Activity {
	
	static String inputName=Common.KEY_NAME_APP;
	static List<File> epubs;
	static List<String> names;
	ArrayAdapter<String> adapter;
	static File selected;
	boolean firstTime;
	File rootDir = Environment.getExternalStorageDirectory();
	private String pathbook=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_chooser_layout);
		/*
		//bookSelector		
		final String pathContaint = rootDir + "/" + Environment.DIRECTORY_PICTURES;
		final String filenamebook ="hongcuu.epub" ;
		final File tempFilebook = new File(pathContaint,filenamebook );
		
		if(tempFilebook.exists())
		{
			pathbook=pathContaint+"/"+filenamebook;
			
		}else{
			pathbook=createExternalStoragePublicPicture().toString()+"/hongcuu.epub";	
			
		}
		*/
		//=======1 cuon sach
		
		pathbook=createExternalStoragePublicPicture().toString()+"/" + inputName +".epub";	
		Intent resultIntent = new Intent();
		resultIntent.putExtra("bpath",pathbook);
		setResult(Activity.RESULT_OK, resultIntent);
		finish();
		//=======end 1 cuon sach
		
		//========================de lam phan mem epub
		/*
		if ((epubs == null) || (epubs.size() == 0)) {
			epubs = epubList(Environment.getExternalStorageDirectory());
		}

		ListView list = (ListView) findViewById(R.id.fileListView);
		names = fileNames(epubs);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, names);

		list.setOnItemClickListener(new OnItemClickListener() {
			

			@Override
			public void onItemClick(AdapterView<?> listView, View itemView,int position, long itemId) {
				selected = epubs.get(position);
				Intent resultIntent = new Intent();
				// TODO: hardcoded string
				resultIntent.putExtra("bpath", selected.getAbsolutePath());
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});

		list.setAdapter(adapter);
		*/
		//========================end de lam phan mem epub
	}

	//---------------------------------------
	// doc va ghi file custom
	File createExternalStoragePublicPicture() {
			
		    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		    File file = new File(path, inputName+".epub");

		    try {
		       
		        path.mkdirs();
		        InputStream is = getResources().openRawResource(R.drawable.ca);
		        OutputStream os = new FileOutputStream(file);
		        byte[] data = new byte[is.available()];
		        is.read(data);
		        os.write(data);
		        is.close();
		        os.close();
		        MediaScannerConnection.scanFile(this,
		                new String[] { file.toString() }, null,
		                new MediaScannerConnection.OnScanCompletedListener() {
		            public void onScanCompleted(String path, Uri uri) {
		                Log.i("ExternalStorage", "Scanned " + path + ":");
		                Log.i("ExternalStorage", "-> uri=" + uri);
		            }
		        });
		    } catch (Exception e) {
		   
		        Log.w("ExternalStorage", "Error writing " + file, e);
		    }
		    return path;
	}
	//---------------------------------------
	
	// TODO: hardcoded string
	private List<String> fileNames(List<File> files) {
		List<String> res = new ArrayList<String>();
		for (int i = 0; i < files.size(); i++) {
			res.add(files.get(i).getName().replace(".epub", ""));
			/*
			 * NOTE: future
			res.add(files.get(i).getName().replace(".epub", "").replace(".e0", ""));
			*/
		}
		return res;
		
	}

	// TODO: hardcoded string
	// TODO: check with mimetype, not with filename extension
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
							res.add(f[i]);
						}

						/*
						 * NOTE: future
						if ((lowerCasedName.endsWith(".epub"))
								|| (lowerCasedName.endsWith(".e0"))) {
							res.add(f[i]);
						}
						*/
					}
				}
			}
		}
		return res;
	}

	private void refreshList() {
		
		epubs = epubList(Environment.getExternalStorageDirectory());
		names.clear();
		names.addAll(fileNames(epubs));
		this.adapter.notifyDataSetChanged();
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.file_chooser, menu);
//		return true;
//	}
//
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.update:
//			refreshList();
//		default:
//			return super.onOptionsItemSelected(item);
//		}
//	}
	

}
