package org.hse.nnbuilder.exception

import java.io.IOException

class HashError(override val message: String, e: IOException) : IOException(message, e)
