package kb50.companyx;

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
import android.widget.Toast;

public class UpdateLocation extends Activity {
	private Location c = new Location();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_location);

		Cursor mCursor = getContentResolver()
				.query(Uri.parse("content://kb50.companyxcontent.companyxcontentprovider/locations/"
						+ getIntent().getExtras().getInt("locationId")), null,
						null, null, null);

		mCursor.moveToFirst();
		String country = mCursor.getString(1);
		String city = mCursor.getString(2);
		String address = mCursor.getString(3);

		c.setCountry(country);
		c.setCity(city);
		c.setAdress(address);

		EditText nameField = (EditText) findViewById(R.id.countryField);
		nameField.setText(c.getCountry());

		EditText websiteField = (EditText) findViewById(R.id.cityField);
		websiteField.setText(c.getCity());

		EditText infoField = (EditText) findViewById(R.id.addressField);
		infoField.setText(c.getAdress());

	}

	public void sluit() {

		startActivity(new Intent(this, CompanyList.class));

	};

	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.updateButton:
			try {

				EditText countryField = (EditText) findViewById(R.id.countryField);

				EditText cityField = (EditText) findViewById(R.id.cityField);

				EditText addressField = (EditText) findViewById(R.id.addressField);

				ContentValues values = new ContentValues();
				values.put("country", countryField.getText().toString());
				values.put("city", cityField.getText().toString());
				values.put("address", addressField.getText().toString());

				getContentResolver()
						.update(Uri
								.parse("content://kb50.companyxcontent.companyxcontentprovider/locations/"
										+ getIntent().getExtras().getInt(
												"locationId")),
								values, null, null);

				Toast toast = Toast.makeText(getApplicationContext(),
						"Updated succesfully!", Toast.LENGTH_SHORT);
				toast.show();

				startActivity(new Intent(this, CompanyList.class));

			} catch (Exception e) {

				Toast toast = Toast.makeText(getApplicationContext(),
						"Failed to update!", Toast.LENGTH_SHORT);
				toast.show();
				e.printStackTrace();
			}
			break;
		case R.id.back:
			this.finish();

			break;
		case R.id.removeButton:
			new AlertDialog.Builder(this)
					.setTitle("Delete entry")
					.setMessage(
							"Are you sure you want to delete this location?")
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									getContentResolver()
											.delete(Uri
													.parse("content://kb50.companyxcontent.companyxcontentprovider/locations/"
															+ getIntent()
																	.getExtras()
																	.getInt("locationId")),
													null, null);
									Toast toast = Toast.makeText(
											getApplicationContext(),
											"Company removed!",
											Toast.LENGTH_SHORT);
									toast.show();
									sluit();

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

			break;

		}
		if (view.getId() == R.id.back) {

			this.finish();

		}
	}

}