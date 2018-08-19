package com.charjay.falsework;


import com.charjay.falsework.bean.Project;


public interface IGenerator {
    
    void generate(Project project, String templateDir, String outputPath, String[] ignoreFileEndWith ) throws Exception;

}
