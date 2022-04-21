package org.hse.nnbuilder.exception

class UserNotFoundException(override val message: String) : RuntimeException(message)