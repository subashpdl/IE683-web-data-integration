package IE683.conflictresolution;


import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Fusible;
import de.uni_mannheim.informatik.dws.winter.model.FusibleValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;

import java.util.Collection;
import java.util.Comparator;

public class Minimum<RecordType extends Matchable & Fusible<SchemaElementType>, SchemaElementType extends Matchable> extends
        ConflictResolutionFunction<Double, RecordType, SchemaElementType> {

    @Override
    public FusedValue<Double, RecordType, SchemaElementType> resolveConflict(
            Collection<FusibleValue<Double, RecordType, SchemaElementType>> values) {

        if (values.size() == 0) {
            return new FusedValue<>((Double) null);
        } else {
            return new FusedValue<>(values.stream().min(Comparator.comparingDouble(FusibleValue::getValue)).get()
                    .getValue());
        }
    }

}
