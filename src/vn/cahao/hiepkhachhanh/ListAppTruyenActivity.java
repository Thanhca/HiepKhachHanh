package vn.cahao.hiepkhachhanh;

import java.util.ArrayList;
import vn.hongcuu.hongcuu.R;

import android.os.Bundle;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ListAppTruyenActivity extends SherlockFragmentActivity{
	
	private ListView ldata;
	private ApdterList adapter;
	private DotoListApp dto;
	private ArrayList<DotoListApp> dataApp = new ArrayList<DotoListApp>();
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_app_truyen);
		getSupportActionBar().hide();
		ldata=(ListView)findViewById(R.id.listView1);
		setdata();
		
		
	}
	
	private void setdata(){
		
		//------------------
		dto=new DotoListApp();
		dto.setFileApk("vn.dutinh.dutinh");
		dto.setNameTruyen(getResources().getString(R.string.dutinh));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.bayngayanai.bayngayanai");
		dto.setNameTruyen(getResources().getString(R.string.bayngayanai));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.coman.coman");
		dto.setNameTruyen(getResources().getString(R.string.comang));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.hongcuu.hongcuu");
		dto.setNameTruyen(getResources().getString(R.string.hongcuu));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.mongphusinh.mongphusinh");
		dto.setNameTruyen(getResources().getString(R.string.mongphusinh));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.neuocsencotinhyeu.neuocsencotinhtyeu");
		dto.setNameTruyen(getResources().getString(R.string.neusocsencotinhyeu));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.socsenchay.ocsenchay");
		dto.setNameTruyen(getResources().getString(R.string.ocsenchay));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.tandio.tandio");
		dto.setNameTruyen(getResources().getString(R.string.tamdio));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.tungco1nguoiyeutoinhusinhmenh.tungco1nguoiyeutoinhusinhmenh");
		dto.setNameTruyen(getResources().getString(R.string.tungco1nguoiyeutoinhusinhmenh));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.bansacthucnu.bansacthucnu");
		dto.setNameTruyen(getResources().getString(R.string.bansacthucnu));
		dataApp.add(dto);
		//-----------------truyen tranh
		dto=new DotoListApp();
		dto.setFileApk("vn.DaiMinhGiangHoTrachNuKy.DaiMinhGiangHoTrachNuKy");
		dto.setNameTruyen(getResources().getString(R.string.DaiMinhGiangHoTrachNuKyDaiMinhGiangHoTrachNuKy));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.daokiemthanhoang.daokiemthanhoang");
		dto.setNameTruyen(getResources().getString(R.string.daokiemthanhoang));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.hoangtaohoangvu.hoangtaohoangvu");
		dto.setNameTruyen(getResources().getString(R.string.hoangtaohoangvu));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.labanvanmenh.labanvanmenh");
		dto.setNameTruyen(getResources().getString(R.string.labanvanmenh));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.satvuong.satvuong");
		dto.setNameTruyen(getResources().getString(R.string.satvuong));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.thanmo.thanmo");
		dto.setNameTruyen(getResources().getString(R.string.thanmo));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.thienhavosong.thienhavosong");
		dto.setNameTruyen(getResources().getString(R.string.thienhavosong));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.thientaidoalac.thientaidoalacs");
		dto.setNameTruyen(getResources().getString(R.string.thientaidoalac));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.thientontrongsinh.thientontrongsinh");
		dto.setNameTruyen(getResources().getString(R.string.thientontrongsinh));
		dataApp.add(dto);
		//-----------------
		dto=new DotoListApp();
		dto.setFileApk("vn.thuphi.thuphi");
		dto.setNameTruyen(getResources().getString(R.string.thuphi));
		dataApp.add(dto);
		//-----------------
		
		adapter = new ApdterList(getBaseContext(), dataApp);
		ldata.setAdapter(adapter);
		
	}

}
