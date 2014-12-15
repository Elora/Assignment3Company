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

public class UpdateOffice extends Activity {

	int locationID;
	List<Location> locationList;
	String[] locations;
	int officeId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_office);

		Cursor cursor = getContentResolver()
				.query(Uri
						.parse("content://kb50.companyxcontent.companyxcontentprovider/offices/" + getIntent().getExtras().getInt("officeId")),
						null, null, null, null);

		cursor.moveToFirst();
		Office office = new Office();

		office.setId(cursor.getInt(0));
		office.setTelNum(Integer.parseInt(cursor.getString(1)));
		officeId = office.getId();
		for (int i = 0; i < getAllLocations().size(); i++) {

			if (getAllLocations().get(i).getId() == cursor.getInt(2)) {

				office.setLocation(getAllLocations().get(i));

			}

		}
	

		String showLocation = office.getLocation().getCountry() + "\n" + office.getLocation().getCity() + "\n"
				+ office.getLocation().getAdress();
		EditText telField = (EditText) findViewById(R.id.telUpdate);
		telField.setText("" + office.getTelNum());
		TextView locationView = (TextView) findViewById(R.id.textViewAddress);
		locationView.setText(showLocation);

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

			EditText phoneField = (EditText) findViewById(R.id.telUpdate);
		
			o.setTelNum(Integer.parseInt(phoneField.getText().toString()));

			ContentValues values = new ContentValues();
			values.put("tel_number", o.getTelNum());
			values.put("location_id", locationID);

			try {
				getContentResolver()
						.update(Uri
								.parse("content://kb50.companyxcontent.companyxcontentprovider/offices/"+officeId),
								values,null,null);

				Toast toast = Toast.makeText(getApplicationContext(),
						"Updated succesfully!", Toast.LENGTH_SHORT);
				toast.show();

			} catch (Exception e) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"Failed to update!", Toast.LENGTH_SHORT);
				toast.show();
				e.printStackTrace();
			}

			startActivity(new Intent(this, OfficeList.class));

		}
		
	
	if(view.getId() == R.id.removeButton){
		new AlertDialog.Builder(this)
				.setTitle("Delete entry")
				.setMessage("Are you sure you want to delete this office?")
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								getContentResolver()
										.delete(Uri
												.parse("content://kb50.companyxcontent.companyxcontentprovider/offices/"
														+ getIntent()
																.getExtras()
																.getInt("officeId")),
												null, null);
								Toast toast = Toast.makeText(
										getApplicationContext(),
										"Office removed!",
										Toast.LENGTH_SHORT);
								toast.show();
								finishActivity();
								
							}
							
						})
				.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// do nothing
							}
						}).setIcon(android.R.drawable.ic_dialog_alert)
				.show();
		
		
	
	}

	if(view.getId() == R.id.back){
		
		this.finish();
		
	}

	}

	private void finishActivity(){
		startActivity(new Intent(this, CompanyList.class));
		this.finish();
		
		
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
