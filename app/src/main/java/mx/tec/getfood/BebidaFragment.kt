package mx.tec.getfood

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import mx.tec.getfood.elemento.adapter.CustomAdapter
import mx.tec.getfood.elemento.model.Elemento
import org.json.JSONArray


class BebidaFragment : Fragment() {

    var lista: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val v = inflater.inflate(R.layout.fragment_bebida, container, false)

        val lista: RecyclerView = v.findViewById(R.id.lvLista)

        val datos = listOf(
            Elemento(1, "Refresco", "Coca, sidralo naranja", "$15", "20", R.drawable.soda),
            Elemento(2, "Jugo", "Manzana, naranja o mango", "$17","20",  R.drawable.jugo),
            Elemento(3, "Limonada", "Rosa o normal", "$23","20",  R.drawable.limonada),
            Elemento(3, "Moca", "Se puede descafeinada", "$24","20",  R.drawable.moca),
            Elemento(4, "Cerveza de Raiz", "No vendemos de verdad", "$30","20",  R.drawable.raiz)
        )

        val adaptador = getActivity()?.let {
            CustomAdapter(
                it,
                R.layout.layout_elemento, datos, R.anim.bounce
            )
        }

        lista.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        lista.setHasFixedSize(true)
        lista.adapter=adaptador
        //showData(v)

        return v
    }
    fun showData(v: View){
        var queue = Volley.newRequestQueue(getActivity())

        lista = v.findViewById(R.id.lvLista)
        var bebidas = ArrayList<Elemento>()

        val uri = "http://10.0.2.2/getfood/bebida"

        val listener = Response.Listener<JSONArray> { response ->
            //Log.e("Mensaje", response.toString())
            for (i in 0 until response.length()) {
                /*var bebida = Elemento(
                    id = response.getJSONObject(i).getInt("idPlatillo"),
                    nombre = response.getJSONObject(i).getString("nombre"),
                    descripcion = response.getJSONObject(i).getString("descripcion"),
                    costo = "$"+response.getJSONObject(i).getString("costo"),
                    puntos = response.getJSONObject(i).getString("puntos"),
                    imagen = response.getJSONObject(i).getString("imagen")
                )
                bebidas.add(bebida)*/
            }
            val adaptador = getActivity()?.let {
                CustomAdapter(
                    it,
                    R.layout.layout_elemento, bebidas, R.anim.bounce
                )
            }
            //adaptador.notifyDataSetChanged()
            lista!!.layoutManager =
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            lista!!.setHasFixedSize(true)
            lista!!.adapter = adaptador
        }

        val error = Response.ErrorListener { error ->
            Log.e("Mensaje", error.message!!)
        }
        val request = JsonArrayRequest(Request.Method.GET, uri, null, listener, error)
        queue.add(request)
    }
}