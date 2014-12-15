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

public class AddCompany extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_company);
		stringToArr();
	}

	List<Office> officeList;
	String[] offices;
	List<Office> selectedOffices = new ArrayList<Office>();
	protected int officeId;

	public void stringToArr() {

		officeList = getAlloffices();
		if (!officeList.isEmpty()) {
			offices = new String[officeList.size()];
			for (int i = 0; i < officeList.size(); i++) {

				Office o = getAlloffices().get(i);
				offices[i] = o.getId() + ",tel: " + o.getTelNum() + " ";

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

			offices.add(office);
			cursor.moveToNext();
		}

		cursor.close();
		return offices;

	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		
		
	}

	public void onClick(View view) {

		if (view.getId() == R.id.add_office) {

			new AlertDialog.Builder(this).setTitle("Select office")
					.setItems(offices, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Office o = officeList.get(which);
							officeId = o.getId();
							selectedOffices.add(o);
							String showOffice = "";
							for (Office of : selectedOffices) {
								showOffice = "Selected office: " + of.getId()
										+ " tel: " + of.getTelNum();

							}
							TextView v = (TextView) findViewById(R.id.viewOffice);
							v.setText(v.getText() + "\n" + showOffice);

						}
					}).show();

		} if(view.getId() == R.id.add_company) {

			Company c = new Company();
			c.setName(((EditText) findViewById(R.id.nameField)).getText()
					.toString());
			c.setSite(((EditText) findViewById(R.id.websiteField)).getText()
					.toString());
			c.setInfo(((EditText) findViewById(R.id.infoField)).getText()
					.toString());

			ContentValues values = new ContentValues();
			values.put("name", c.getName());
			values.put("website", c.getSite());
			values.put("info", c.getInfo());

			try {
				getContentResolver()
						.insert(Uri
								.parse("content://kb50.companyxcontent.companyxcontentprovider/company"),
								values);

				Cursor cursor = getContentResolver()
						.query(Uri
								.parse("content://kb50.companyxcontent.companyxcontentprovider/company"),
								null, null, null, null);
				cursor.moveToLast();
				ContentValues compOffice = new ContentValues();
				compOffice.put("company_id", cursor.getInt(0));

				for(Office office : selectedOffices){
					
					getContentResolver()
					.update(Uri
							.parse("content://kb50.companyxcontent.companyxcontentprovider/offices/"+office.getId()),
							compOffice, null, null);
					
				}
			

				Toast toast = Toast.makeText(getApplicationContext(),
						"Added succesfully!", Toast.LENGTH_SHORT);
				toast.show();

			} catch (Exception e) {

				Toast toast = Toast.makeText(getApplicationContext(),
						"Failed to add!", Toast.LENGTH_SHORT);
				toast.show();
				e.printStackTrace();
			}

			Intent i = new Intent(this, CompanyList.class);
			startActivity(i);

		}
		if(view.getId() == R.id.addImageButton){
			
			Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i, 1);
		}
		
	}
}