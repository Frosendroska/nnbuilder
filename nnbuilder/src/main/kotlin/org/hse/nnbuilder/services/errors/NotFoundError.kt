package org.hse.nnbuilder.services.errors

class NotFoundError(
    id: Any,
    objName: String,
) : RuntimeException("Object of $objName not found with id $id")
