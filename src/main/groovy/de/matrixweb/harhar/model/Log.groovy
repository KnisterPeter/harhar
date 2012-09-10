package de.matrixweb.harhar.model


/**
 * @author marwol
 */
class Log implements Serializable {

  String version

  Creator creator

  List<Page> pages = new LinkedList<Page>()

  List<Entry> entries = new LinkedList<Entry>()

  List<Entry> getEntriesByPage(final Page page) {
    entries.findAll {
      it.getPageref() == page.getId() && it.getRequest().getUrl().startsWith("http")
    }
  }
}
