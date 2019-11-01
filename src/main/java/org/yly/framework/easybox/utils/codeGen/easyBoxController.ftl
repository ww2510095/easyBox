package ${packages};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yly.framework.easybox.mybatis.Controller.EasyBoxBaseController;

@RestController
@RequestMapping("${packages}")
public class ${className}Controller extends EasyBoxBaseController<${className}> {

  	@Autowired
    private ${className}Service c${className}Service;

    @Override
    protected ${className}Service getService() {
        return c${className}Service;
    }
  
}