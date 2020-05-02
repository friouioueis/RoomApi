  package com.example.exo6_tdpersistance


import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*


  class MainActivity : AppCompatActivity() {
      val ADD_NOTE_REQUEST = 1
      private val EDIT_NOTE_REQUEST = 3
      private var appDatabase: AppDatabase? = null
      private var adapter:ListViewAdapter?= null
      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_main)
          appDatabase = AppDatabase.buildDatabase(this)!!


          //List view initilaziation
          val listView = findViewById<ListView>(R.id.list)
          val module_filter = findViewById<AutoCompleteTextView>(R.id.module_filter)




            val jsonList = "[\n" +
                    "\t\n" +
                    "\t{\n" +
                    "\t\t\tgroupe:1,\n" +
                    "\t\t\tanee:2020,\n" +
                    "\t\t\tjour:1,\n" +
                    "\t\t\theure:8,\n" +
                    "\t\t\tmodule:\"physics\",\n" +
                    "\t\t\tsalle:2,\n" +
                    "\t\t\tenseignant:\"hannoun\"\n" +
                    "\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t},\n" +
                    "\n" +
                    "\t\t\t{\n" +
                    "\t\t\tgroupe:2,\n" +
                    "\t\t\tanee:2020,\n" +
                    "\t\t\tjour:1,\n" +
                    "\t\t\theure:10,\n" +
                    "\t\t\tmodule:\"math\",\n" +
                    "\t\t\tsalle:2,\n" +
                    "\t\t\tenseignant:\"benchabane\"\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t},\n" +
                    "\t\n" +
                    "\t\t{\n" +
                    "\t\t\tgroupe:1,\n" +
                    "\t\t\tanee:2020,\n" +
                    "\t\t\tjour:8,\n" +
                    "\t\t\theure:18,\n" +
                    "\t\t\tmodule:\"history\",\n" +
                    "\t\t\tsalle:4,\n" +
                    "\t\t\tenseignant:\"chomesky\"\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t},\n" +
                    "\t{\n" +
                    "\t\t\tgroupe:2,\n" +
                    "\t\t\tanee:2020,\n" +
                    "\t\t\tjour:5,\n" +
                    "\t\t\theure:1,\n" +
                    "\t\t\tmodule:\"mobile2\",\n" +
                    "\t\t\tsalle:3,\n" +
                    "\t\t\tenseignant:\"mostefai\"\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t},\n" +
                    "\t\t\t{\n" +
                    "\t\t\tgroupe:1,\n" +
                    "\t\t\tanee:2020,\n" +
                    "\t\t\tjour:5,\n" +
                    "\t\t\theure:1,\n" +
                    "\t\t\tmodule:\"Math\",\n" +
                    "\t\t\tsalle:3,\n" +
                    "\t\t\tenseignant:\"Hamidi\"\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t},\n" +
                    "\t\t\t{\n" +
                    "\t\t\tgroupe:2,\n" +
                    "\t\t\tanee:2020,\n" +
                    "\t\t\tjour:5,\n" +
                    "\t\t\theure:1,\n" +
                    "\t\t\tmodule:\"ALOG\",\n" +
                    "\t\t\tsalle:3,\n" +
                    "\t\t\tenseignant:\"Cehbieb\"\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t},\n" +
                    "\t\t\t{\n" +
                    "\t\t\tgroupe:3,\n" +
                    "\t\t\tanee:2020,\n" +
                    "\t\t\tjour:9,\n" +
                    "\t\t\theure:1,\n" +
                    "\t\t\tmodule:\"PDJ\",\n" +
                    "\t\t\tsalle:3,\n" +
                    "\t\t\tenseignant:\"CHALLAL\"\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t},\n" +
                    "\t\t\t{\n" +
                    "\t\t\tgroupe:2,\n" +
                    "\t\t\tanee:2020,\n" +
                    "\t\t\tjour:11,\n" +
                    "\t\t\theure:1,\n" +
                    "\t\t\tmodule:\"ML\",\n" +
                    "\t\t\tsalle:1,\n" +
                    "\t\t\tenseignant:\"Aries\"\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t},\n" +
                    "\t\t\t\t{\n" +
                    "\t\t\tgroupe:1,\n" +
                    "\t\t\tanee:2020,\n" +
                    "\t\t\tjour:3,\n" +
                    "\t\t\theure:1,\n" +
                    "\t\t\tmodule:\"Qlog\",\n" +
                    "\t\t\tsalle:2,\n" +
                    "\t\t\tenseignant:\"zakaria\"\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t},\n" +
                    "\t\t\t\t{\n" +
                    "\t\t\tgroupe:3,\n" +
                    "\t\t\tanee:2020,\n" +
                    "\t\t\tjour:11,\n" +
                    "\t\t\theure:16,\n" +
                    "\t\t\tmodule:\"ihm\",\n" +
                    "\t\t\tsalle:1,\n" +
                    "\t\t\tenseignant:\"abdenour\"\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t},\n" +
                    "\t\t\t\t{\n" +
                    "\t\t\tgroupe:1,\n" +
                    "\t\t\tanee:2020,\n" +
                    "\t\t\tjour:11,\n" +
                    "\t\t\theure:8,\n" +
                    "\t\t\tmodule:\"religion\",\n" +
                    "\t\t\tsalle:1,\n" +
                    "\t\t\tenseignant:\"Hichem\"\n" +
                    "\t\t},\n" +
                    "\n" +
                    "\t\t{\n" +
                    "\t\t\tgroupe:2,\n" +
                    "\t\t\tanee:2020,\n" +
                    "\t\t\tjour:9,\n" +
                    "\t\t\theure:1,\n" +
                    "\t\t\tmodule:\"HPC\",\n" +
                    "\t\t\tsalle:3,\n" +
                    "\t\t\tenseignant:\"riad\"\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t}\t\t\n" +
                    "\t\t\t\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\t\t]"
          //this

          //request prmission ij runtime
         /* requestPermissions(
              arrayOf(
                  WRITE_EXTERNAL_STORAGE,
                  READ_EXTERNAL_STORAGE
              ), 1
          )*/

          save(jsonList)

         btnSave.setOnClickListener {

             //this snippet convert our json file to instances of Seance
             val gson = Gson()
             val arrayTutorialType = object : TypeToken<Array<Seance>>() {}.type
             var jsonString = load()

             //list of all Seance resided in the initial json file "jsonfile"
             val seances: Array<Seance> = gson.fromJson(jsonList, arrayTutorialType)


             adapter= ListViewAdapter(this,R.layout.activity_main,seances.toMutableList())

             seances.forEachIndexed { idx, tut -> println("> Item ${idx}:\n${tut}") }

             //saveJson file to the local storage

                //  val seanceObj = Seance(3, 2014, 23, 3, "Math", 3, "batata")
                  InsertSeances(this, seances).execute()



             listView.adapter = adapter

          }


          val db = Room.databaseBuilder(
              applicationContext,
              AppDatabase::class.java, "emploi.db"
          ).build()

          module_filter.setAdapter(adapter)


          module_filter.setOnItemClickListener{ parent, _, position, id ->
              Toast.makeText(this, "module filter clicked", Toast.LENGTH_LONG).show()

              val selectedSeance = parent.adapter.getItem(position) as Seance?
                print("selected seance : "+selectedSeance?.module)
              module_filter.setText(selectedSeance?.module)

          }
          salle_filter.addTextChangedListener(object : TextWatcher {
              override fun afterTextChanged(p0: Editable?) {
              }

              override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
              }

              override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                  Toast.makeText(this@MainActivity, "CharQequence: "+p0, Toast.LENGTH_LONG).show()

                  adapter!!.getFilter().filter(p0);
              }
          })

          jour_filter.addTextChangedListener(object : TextWatcher {
              override fun afterTextChanged(p0: Editable?) {
              }

              override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
              }

              override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
              }
          })




/*


   val id: Int,
        val jour: Int,
        val heure:Int,
        val module: String,
        val salle:Int,
        val enseignant:String
 */


          GlobalScope.launch {

            //load initialjson data
              val jsonSeaces= load()

              val data = db.seanceDao().getAll()
              data?.forEach {
                  println("this is a seance"+it)
                  Log.d("SEANCE","seance"+it)
                // tvDatafromDb.setText("le module"+it.module)

              }
        }

       /* btnDisplay.setOnClickListener {
              GetDataFromDb(this).execute()
          }*/


      }


      private class InsertSeances(var context: MainActivity, val seances: Array<Seance>) :
          AsyncTask<Void, Void, Boolean>() {
          override fun doInBackground(vararg params: Void?): Boolean {

              context.appDatabase!!.seanceDao().insertAll(seances)
              return true
          }

          override fun onPostExecute(bool: Boolean?) {
              if (bool!!) {
                  Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
              }
          }
      }

      private class GetDataFromDb(var context: MainActivity) :
          AsyncTask<Void, Void, List<Seance>>() {
          override fun doInBackground(vararg params: Void?): List<Seance> {
              return context.appDatabase!!.seanceDao().getAll()
          }

          override fun onPostExecute(seanceList: List<Seance>?) {
              if (seanceList!!.size > 0) {
                  for (i in 0..seanceList.size - 1) {
                     // context.tvDatafromDb.append(seanceList[i].module)
                  }
              }
          }
      }

      fun save(jsonFile:String) {

          var fos: FileOutputStream? = null
          try {
              fos = openFileOutput("seances.json", Context.MODE_PRIVATE)
              fos.write(jsonFile.toByteArray())

              Toast.makeText(
                  this, "Saved to $filesDir/seances.json",
                  Toast.LENGTH_LONG
              ).show()
          } catch (e: FileNotFoundException) {
              e.printStackTrace()
          } catch (e: IOException) {
              e.printStackTrace()
          } finally {
              if (fos != null) {
                  try {
                      fos.close()
                  } catch (e: IOException) {
                      e.printStackTrace()
                  }
              }
          }
      }


          fun load(): String {
              var fis: FileInputStream? = null
              var response = ""
              try {
                  fis = openFileInput("seances.json")
                  val isr = InputStreamReader(fis)
                  val br = BufferedReader(isr)
                  print("br variable : "+br)
                  val sb = StringBuilder()
                  var text: String?
                  while (br.readLine().also { text = it } != null) {
                      sb.append(text).append("\n")
                  }
                  /*Toast.makeText(
                  this, sb.toString(),
                  Toast.LENGTH_LONG
              ).show()*/

                  response = sb.toString()


              } catch (e: FileNotFoundException) {
                  e.printStackTrace()
              } catch (e: IOException) {
                  e.printStackTrace()
              } finally {
                  if (fis != null) {
                      try {
                          fis.close()
                      } catch (e: IOException) {
                          e.printStackTrace()
                      }
                  }
              }


              return response
          }

      fun loadAsJson(): String {
          var fis: FileInputStream? = null
          var response = ""
          try {
              fis = openFileInput("seances.json")
              val isr = InputStreamReader(fis)
              val br = BufferedReader(isr)

              print("br variable : "+br)
              val sb = StringBuilder()
              var text: String?
              while (br.readLine().also { text = it } != null) {
                  sb.append(text).append("\n")
              }
              /*Toast.makeText(
              this, sb.toString(),
              Toast.LENGTH_LONG
          ).show()*/

              response = sb.toString()


          } catch (e: FileNotFoundException) {
              e.printStackTrace()
          } catch (e: IOException) {
              e.printStackTrace()
          } finally {
              if (fis != null) {
                  try {
                      fis.close()
                  } catch (e: IOException) {
                      e.printStackTrace()
                  }
              }
          }


          return response
      }

  }



