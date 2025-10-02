package org.practice.lld.docexport_template;

public class Main {
    public static void main(String[] args) {

        Document d1 = new Document("doc1", "Nothing much a test document");

        FileExporter exporter1 = new PdfDocumentExporter(d1);
        FileExporter exporter2 = new HtmlDocumentExporter(d1);

        exporter1.export();
        exporter2.export();

    }
}

interface FileExporter{
    void export();
}

abstract class DocumentExporter implements FileExporter{

    public abstract void openDoc();
    public abstract void writeDoc();
    public abstract void addMetaData();
    public abstract void closeDoc();
    public abstract boolean shouldAddMetaData();

    @Override
    public void export() {
        openDoc();
        writeDoc();
        if(shouldAddMetaData()) addMetaData();
        closeDoc();
    }
}

class PdfDocumentExporter extends DocumentExporter{

    Document document;

    public PdfDocumentExporter(Document document) {
        this.document = document;
    }

    @Override
    public void openDoc() {
        System.out.println("Opening doc in PDF exporter");
    }

    @Override
    public void writeDoc() {
        System.out.println("Writing doc in PDF exporter");
    }

    @Override
    public void addMetaData() {
        System.out.println("Adding meta data to doc in PDF exporter");
    }

    @Override
    public void closeDoc() {
        System.out.println("Closing doc in PDF exporter");
    }

    @Override
    public boolean shouldAddMetaData() {
        return false;
    }
}

class HtmlDocumentExporter extends DocumentExporter{

    Document document;

    public HtmlDocumentExporter(Document document) {
        this.document = document;
    }

    @Override
    public void openDoc() {
        System.out.println("Opening doc in HTML exporter");
    }

    @Override
    public void writeDoc() {
        System.out.println("Writing doc in HTML exporter");
    }

    @Override
    public void addMetaData() {
        System.out.println("Adding meta data to doc in HTML exporter");
    }

    @Override
    public void closeDoc() {
        System.out.println("Closing doc in HTML exporter");
    }

    @Override
    public boolean shouldAddMetaData() {
        return true;
    }
}

class Document{
    String name;
    String Content;

    public Document(String name, String content) {
        this.name = name;
        Content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return Content;
    }
}
