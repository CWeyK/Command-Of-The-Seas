package CommandOfTheSeas.view

import CommandOfTheSeas.Main
import scalafxml.core.macros.sfxml

@sfxml
class Tutorial1Controller{
  def next(): Unit = {
    Main.showTutorial2()
  }
  def menu(): Unit = {
    Main.showStartMenu()
  }
}
