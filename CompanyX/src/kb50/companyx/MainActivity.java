package kb50.companyx;



import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	
		
		
		ContentValues companyValues = new ContentValues();
		companyValues.put("name", "some commpany info");
		companyValues.put("website","Google inc");
		companyValues.put("info","Google.com");
		
		ContentValues locationtValues = new ContentValues();
		locationtValues.put("address", "adsstraat 22");
		locationtValues.put("city", "Den Haag");
		locationtValues.put("country", "Nederland");

		getContentResolver()
				.insert(Uri
						.parse("content://com.example.appcontentprovider.CompanyProvider/company"),
						companyValues);

		getContentResolver()
				.insert(Uri
						.parse("content://com.example.appcontentprovider.CompanyProvider/location"),
						locationtValues);

		
		//String[] mProjection = {"id"};

		
		
		Cursor mCursor = getContentResolver().query(
				Uri
				.parse("content://com.example.appcontentprovider.CompanyProvider/companies/2"), null, null, null,
				null);

		if (mCursor != null && mCursor.moveToFirst()) {
			String num = mCursor.getString(mCursor.getColumnIndex("info"));
			mCursor.close();

			Toast.makeText(this, "info: " + num, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
