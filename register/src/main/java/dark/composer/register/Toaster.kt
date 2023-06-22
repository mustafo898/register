package dark.composer.register

import android.content.Context
import android.widget.Toast

class Toaster {
    fun showMessage(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}