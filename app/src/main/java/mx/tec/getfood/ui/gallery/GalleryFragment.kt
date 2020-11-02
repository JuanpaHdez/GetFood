package mx.tec.getfood.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.android.synthetic.main.fragment_codigo.*
import kotlinx.android.synthetic.main.fragment_codigo.view.*
import mx.tec.getfood.LogIn
import mx.tec.getfood.Menu
import mx.tec.getfood.R
import org.json.JSONObject

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_codigo, container, false)
        val textView: TextView = root.findViewById(R.id.edt_codigo)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        root.btn_codigo.setOnClickListener {
            Toast.makeText(getActivity(), "Codigo Registrado", Toast.LENGTH_SHORT).show()
            val codigo = edt_codigo.text.toString()
            addCodigo(codigo)
            edt_codigo.setText("")

        }
        return root
    }

    fun addCodigo(codigo:String){
        var json = JSONObject()
        json.put("codigo", codigo)

        var resp = false;
        val uri = "http://10.0.2.2/getfood/registroCodigo"
        var queue = Volley.newRequestQueue(getActivity())
        val listener = Response.Listener<JSONObject> { response ->
            Log.e("Mensaje", response.toString())
            resp=true
        }
        val error = Response.ErrorListener { error ->
            Log.e("Mensaje", error.message!!)
        }

        val request = JsonObjectRequest(Request.Method.POST, uri, json, listener, error)
        queue.add(request)
    }
}