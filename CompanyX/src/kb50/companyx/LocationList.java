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

public class LocationList extends ListActivity {

	List<Location> locationList;
	String[] locations;

	public void stringToArr() {

		locationList = getAllLocations();
		locations = new String[locationList.size()];
		for (int i = 0; i < locationList.size(); i++) {

			Location l = getAllLocations().get(i);
			locations[i] = l.getCountry()+", "+l.getCity()+", "+l.getAdress();

		}

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
				android.R.layout.simple_list_item_1, locations));
	}

	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {

		Intent i = new Intent(this, UpdateLocation.class);
		i.putExtra("locationId", locationList.get(position).getId());
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
