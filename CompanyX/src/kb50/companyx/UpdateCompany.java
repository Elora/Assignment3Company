package kb50.companyx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateCompany extends Activity {
	private Company c = new Company();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_company);

		Cursor mCursor = getContentResolver()
				.query(Uri.parse("content://com.example.appcontentprovider.CompanyProvider/companies/"
						+ getIntent().getExtras().getInt("companyId")), null,
						null, null, null);

		mCursor.moveToFirst();
		String name = mCursor.getString(1);
		String website = mCursor.getString(2);
		String info = mCursor.getString(3);

		c.setName(name);
		c.setSite(website);
		c.setInfo(info);

		EditText nameField = (EditText) findViewById(R.id.nameField);
		nameField.setText(c.getName());

		EditText websiteField = (EditText) findViewById(R.id.websiteField);
		websiteField.setText(c.getSite());

		EditText infoField = (EditText) findViewById(R.id.infoField);
		infoField.setText(c.getInfo());

	}
	public void sluit(){
		
		startActivity(new Intent(this, CompanyList.class));

		
	};

	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.updateButton:
			try {

				EditText nameField = (EditText) findViewById(R.id.nameField);

				EditText websiteField = (EditText) findViewById(R.id.websiteField);

				EditText infoField = (EditText) findViewById(R.id.infoField);

				ContentValues values = new ContentValues();
				values.put("name", nameField.getText().toString());
				values.put("website", websiteField.getText().toString());
				values.put("info", infoField.getText().toString());

				getContentResolver()
						.update(Uri.parse("content://com.example.appcontentprovider.CompanyProvider/companies/"
								+ getIntent().getExtras().getInt("companyId")),
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
		case R.id.backButton:
			this.finish();

			break;
		case R.id.removeButton:
			new AlertDialog.Builder(this)
					.setTitle("Delete entry")
					.setMessage("Are you sure you want to delete this entry?")
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									getContentResolver()
											.delete(Uri
													.parse("content://com.example.appcontentprovider.CompanyProvider/companies/"
															+ getIntent()
																	.getExtras()
																	.getInt("companyId")),
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

	}
}