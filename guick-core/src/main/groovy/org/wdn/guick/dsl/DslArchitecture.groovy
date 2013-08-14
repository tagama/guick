package org.wdn.guick.dsl

import org.springframework.stereotype.Component
import org.wdn.guick.common.GdslReader
import org.wdn.guick.model.Project

import javax.annotation.Resource

/**
 * Created with IntelliJ IDEA.
 * User: walter
 * Date: 8/13/13
 * Time: 10:43 PM
 */
@Component
class DslArchitecture {

    @Resource TargetDelegate targetDelegate
    @Resource Project project

    @Resource GdslReader reader

    void runEngine(String target) {

        Binding binding = new Binding()
        binding.setVariable("project", project)

        Script dslScript = new GroovyShell(binding).parse(reader.get(target))

        dslScript.metaClass = createEMC(dslScript.class, {
            ExpandoMetaClass emc ->
                emc.guick = {
                    Closure cl ->
                        cl.delegate = targetDelegate
                        cl.resolveStrategy = Closure.DELEGATE_FIRST
                        cl()
                }
        })
        dslScript.run()
    }

    private ExpandoMetaClass createEMC(Class clazz, Closure cl) {
        ExpandoMetaClass emc = new ExpandoMetaClass(clazz, false)
        cl(emc)
        emc.initialize()
        return emc
    }


}
