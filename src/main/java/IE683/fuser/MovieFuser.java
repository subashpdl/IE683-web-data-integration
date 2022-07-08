package IE683.fuser;

import IE683.model.Movie;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public abstract class MovieFuser<T> extends AttributeValueFuser<T, Movie, Attribute> {
    private final Method setAttributeMethod;
    private final Attribute attribute;

    public MovieFuser(ConflictResolutionFunction<T, Movie, Attribute> conflictResolutionFunction,
                       Attribute attribute, Class attributeClass) throws NoSuchMethodException {
        super(conflictResolutionFunction);
        this.setAttributeMethod = Movie.class.getMethod("set" + StringUtils.capitalize(attribute.getIdentifier()),
                attributeClass);
        this.attribute = attribute;
    }

    @Override
    public void fuse(RecordGroup<Movie, Attribute> group, Movie fusedRecord,
                     Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {

        // get the fused value
        FusedValue<T, Movie, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);

        // set the value for the fused record
        try {
            this.setAttributeMethod.invoke(fusedRecord, fused.getValue());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // add provenance info
        fusedRecord.setAttributeProvenance(attribute, fused.getOriginalIds());

    }

    @Override
    public boolean hasValue(Movie record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(attribute);
    }

    @Override
    public T getValue(Movie record, Correspondence<Attribute, Matchable> correspondence) {
        try {
            return (T) Movie.class.getMethod("get" + StringUtils.capitalize(attribute.getIdentifier()))
                    .invoke(record);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
