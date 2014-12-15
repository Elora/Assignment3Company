package kb50.companyx;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddLocation extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_location);
		stringToArr();
	}

	List<Location> locationList;
	String[] locations;

	public void onClick(View view) {

		Location l = new Location();
		l.setCountry(((EditText) findViewById(R.id.countryField)).getText()
				.toString());
		l.setCity(((EditText) findViewById(R.id.cityField)).getText()
				.toString());
		l.setAdress(((EditText) findViewById(R.id.addressField)).getText()
				.toString());

		ContentValues values = new ContentValues();
		values.put("country", l.getCountry());
		values.put("city", l.getCity());
		values.put("address", l.getAdress());

		try {
			getContentResolver()
					.insert(Uri
							.parse("content://kb50.companyxcontent.companyxcontentprovider/location"),
							values);

			Toast toast = Toast.makeText(getApplicationContext(),
					"Added succesfully!", Toast.LENGTH_SHORT);
			toast.show();

		} catch (Exception e) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Failed to add!", Toast.LENGTH_SHORT);
			toast.show();
			e.printStackTrace();
		}

		Intent i = new Intent(this, LocationList.class);
		startActivity(i);

	}

	public void stringToArr() {

		locationList = getAllLocations();
		locations = new String[locationList.size()];
		for (int i = 0; i < locationList.size(); i++) {

			Location l = getAllLocations().get(i);
			locations[i] = l.getCountry() + ", " + l.getCity() + ", "
					+ l.getAdress();

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

}