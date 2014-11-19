package org.wdn.guick.support

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.wdn.guick.model.Project
import org.wdn.guick.model.Entity
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: y1z5
 * Date: 02/05/12
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
@Component
class SequenceNameBRP implements IBusinessRulesProcessor {

    private final Logger logger = LoggerFactory.getLogger(this.class)

    @Override
    Project doProcess(Project project) {
        for (Entity entity : project.getEntities()) {
            if (entity.table != null && entity.table.sequenceName == null) {
                try {
                    entity.table.sequenceName = "SQ_" + entity.table.getPk()[0].name
                } catch (e) {
                    logger.println("Entity without Table or no PK ..." + entity.table.name)
                }
            }
        }
        return project;
    }
}
