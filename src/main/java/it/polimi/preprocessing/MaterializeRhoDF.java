/**
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.polimi.preprocessing;

import it.polimi.rsp.baselines.utils.FileUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Log4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterializeRhoDF {
    static Reasoner reasoner;

    public static void main(String[] args) throws Exception {

        Model m = FileManager.get().loadModel("src/main/resources/data/inference/univ-bench-rdfs.rdf", null, "RDF/XML");

        reasoner = getReducedReasoner();
        InfModel infmodel = ModelFactory.createInfModel(reasoner, m);

        File file = new File("src/main/resources/data/inference/univ-bench-rdfs-materialized-rhodf.rdf");

        try (FileOutputStream fop = new FileOutputStream(file)) {
            if (!file.exists()) {
                file.createNewFile();
            }
            infmodel.write(fop);

            fop.flush();
            fop.close();

        } catch (IOException e) {
            log.error(e.getMessage());
        }

        log.info("Done");
    }

    private static Reasoner getReducedReasoner() {
        List<Rule> rules = Rule.rulesFromURL(FileUtils.RHODF_RULE_SET);
        return new GenericRuleReasoner(rules);
    }
}
