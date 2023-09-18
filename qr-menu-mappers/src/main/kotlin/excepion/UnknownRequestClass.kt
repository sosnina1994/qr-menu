package excepion

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class $clazz cannot be mapped to QrMenuContext")
