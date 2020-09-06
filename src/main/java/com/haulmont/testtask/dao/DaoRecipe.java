package com.haulmont.testtask.dao;

import com.haulmont.testtask.pojo.Recipe;

import java.util.List;

public interface DaoRecipe {

    List<Recipe> getAllRecipe();

    boolean setRecipe(Recipe recipe);

    boolean updateRecipeByID(Recipe recipe);

    boolean deleteRecipeByID(long id);
}
