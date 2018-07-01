package reptxstudio.journalapp.mvp.view;

public interface ArticleEditView {
    void error(String message);

    void errorTitle(String titre_vide);

    void errorContent(String contenu_vide);

    void dateError(String there_is_no_date);

    void ArticlehasbeenInserted(String message);
}
