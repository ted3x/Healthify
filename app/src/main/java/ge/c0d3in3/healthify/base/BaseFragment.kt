package ge.c0d3in3.healthify.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import ge.c0d3in3.healthify.utils.AlertDialog
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(
    private val inflate: Inflate<VB>
) : Fragment() {

    protected abstract var module: Module
    protected abstract val vm: VM
    private var _binding: VB? = null
    protected val binding
        get() = if (_binding == null)
            throw IllegalStateException("${this::class.java.simpleName}'s binding is null")
        else _binding

    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(module)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return _binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        dialog = null
        unloadKoinModules(module)
    }

    fun <T> LiveData<T>.observe(callback: (T) -> Unit) {
        this.observe(viewLifecycleOwner, Observer {
            callback.invoke(it)
        })
    }

    protected fun getDialog(): AlertDialog {
        if(dialog == null)
            dialog = AlertDialog(requireContext())
        return dialog!!
    }
}

