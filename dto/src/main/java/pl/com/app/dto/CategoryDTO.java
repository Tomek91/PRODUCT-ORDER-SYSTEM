package pl.com.app.dto;




import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;

import java.util.Objects;

public class CategoryDTO {
    private Long id;
    private String name;

    public static CategoryDTO.CategoryDTOBuilder builder() {
        return new CategoryDTO.CategoryDTOBuilder();
    }

    public CategoryDTO(Long id, String name) {
        setId(id);
        setName(name);
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {

        try{
            if (name == null){
                throw new NullPointerException("NAME IS NULL");
            } else if (!name.matches("[A-Z ]+")){
                throw new IllegalArgumentException("NAME VALUE IS INCORRECT");
            }
            this.name = name;
        } catch (Exception e){
            throw new MyException(String.join(";",
                    this.getClass().getCanonicalName(),
                    EErrorsMessage.VALIDATION_NAME_ERROR.toString()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDTO that = (CategoryDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    protected boolean canEqual(Object other) {
        return other instanceof CategoryDTO;
    }

    public String toString() {
        return "CategoryDTO(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    public CategoryDTO() {
    }

    public static class CategoryDTOBuilder {
        private Long id;
        private String name;

        CategoryDTOBuilder() {
        }

        public CategoryDTO.CategoryDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CategoryDTO.CategoryDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CategoryDTO build() {
            return new CategoryDTO(this.id, this.name);
        }

        public String toString() {
            return "CategoryDTO.CategoryDTOBuilder(id=" + this.id + ", name=" + this.name + ")";
        }
    }
}
