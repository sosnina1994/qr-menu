package excepion

import models.EQrMenuCommand

class UnknownQrMenuCommand(command: EQrMenuCommand) : Throwable("Wrong command $command at mapping toTransport stage")
