package CommandOfTheSeas.view

import CommandOfTheSeas.Main
import scalafxml.core.macros.sfxml

@sfxml
class Tutorial2Controller{
  def next(): Unit = {
    Main.showTutorial3()
  }
  def previous(): Unit = {
    Main.showTutorial1()
  }
}
