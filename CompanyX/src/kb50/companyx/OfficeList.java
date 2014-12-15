package kb50.companyx;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class OfficeList extends ListActivity {

	List<Office> officeList;
	String[] offices;

	public void stringToArr() {

		
		officeList = getAlloffices();
		if(!officeList.isEmpty()){
		offices = new String[officeList.size()];
		for (int i = 0; i < officeList.size(); i++) {

			Office o = getAlloffices().get(i);
			offices[i] = o.getId()+", "+o.getTelNum()+", "+o.getLocation().getCity();

		}
		}
	}

	public List<Office> getAlloffices() {
		List<Office> offices = new ArrayList<Office>();

		Cursor cursor = getContentResolver()
				.query(Uri
						.parse("content://kb50.companyxcontent.companyxcontentprovider/office"),
						null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Office office = new Office();

			office.setId(cursor.getInt(0));
			office.setTelNum(Integer.parseInt(cursor.getString(1)));
			
			if(!getAllLocations().isEmpty()){
			for(int i = 0; i<getAllLocations().size(); i++){
				
				if(getAllLocations().get(i).getId() == cursor.getInt(2)){
					
					office.setLocation(getAllLocations().get(i));
					
				}
				
				
			}
			if(office.getLocation() != null){
				offices.add(office);
				
			}
		
			}
			
		
			cursor.moveToNext();
		}

		cursor.close();
		return offices;

	}
	
	public List<Location> getAllLocations() {
		List<Location> locations = new ArrayList<Location>();

		Cursor cursor = getContentResolver()
				.query(Uri
						.parse("content://kb50.companyxcontent.companyxcontentprovider/location"),
						null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Location location = new Location();

			location.setId(cursor.getInt(0));
			location.setCountry(cursor.getString(1));
			location.setCity(cursor.getString(2));
			location.setAdress(cursor.getString(3));
			locations.add(location);
			cursor.moveToNext();
		}

		cursor.close();
		return locations;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		stringToArr();
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, offices));
	}

	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {

		Intent i = new Intent(this, UpdateOffice.class);
		i.putExtra("officeId", officeList.get(position).getId());
		startActivity(i);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	super.onOptionsItemSelected(item);
		
		int id = item.getItemId();
		if (id == R.id.add_company) {
			startActivity(new Intent(this,AddCompany.class));
		}
		if (id == R.id.add_location) {
			startActivity(new Intent(this,AddLocation.class));
		}
		if (id == R.id.add_office) {
			startActivity(new Intent(this,AddOffice.class));
		}
		
		return true;
	}

}
