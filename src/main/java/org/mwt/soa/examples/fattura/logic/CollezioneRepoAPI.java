package org.mwt.soa.examples.fattura.logic;

import java.util.List;

public interface CollezioneRepoAPI {

    List<Collezione> getCollezione() throws CollezioneRepoError;

}
