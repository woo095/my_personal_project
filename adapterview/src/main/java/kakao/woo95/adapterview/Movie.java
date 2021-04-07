package kakao.woo95.adapterview;

public class Movie {
    private String title;
    private String subtitle;
    private double rating;
    private String thumbnail;
    private String link;

    public Movie() {
    }

    public Movie(String title, String subtitle, double rating, String thumbnail, String link) {
        this.title = title;
        this.subtitle = subtitle;
        this.rating = rating;
        this.thumbnail = thumbnail;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", rating=" + rating +
                ", thumbnail='" + thumbnail + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
