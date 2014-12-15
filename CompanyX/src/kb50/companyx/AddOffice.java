package kb50.companyx;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddOffice extends Activity {

	List<Location> locationList;
	String[] locations;
	int locationID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_office);
		stringToArr();

	}

	
	

	public void onClick(View view) {

		if (view.getId() == R.id.add_locationButton) {

			new AlertDialog.Builder(this).setTitle("Select location")
					.setItems(locations, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Location l = locationList.get(which);
							locationID = l.getId();
							String showLocation = l.getCountry() + "\n"
									+ l.getCity() + "\n" + l.getAdress();

							TextView v = (TextView) findViewById(R.id.textViewAddress);
							v.setText(showLocation);
						}
					}).show();

		}

		if (view.getId() == R.id.addOfficeButton) {

			Office o = new Office();

			EditText phoneField = (EditText) findViewById(R.id.phoneField);
			o.setTelNum(Integer.parseInt(phoneField.getText().toString()));

			ContentValues values = new ContentValues();
			values.put("tel_number", o.getTelNum());
			values.put("location_id", locationID);

			try {
				getContentResolver()
						.insert(Uri
								.parse("content://kb50.companyxcontent.companyxcontentprovider/office"),
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
			
			startActivity(new Intent(this,OfficeList.class));

		}

	}

	public void stringToArr() {

		locationList = getAllLocations();
		locations = new String[locationList.size()];
		for (int i = 0; i < locationList.size(); i++) {

			Location l = locationList.get(i);
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
