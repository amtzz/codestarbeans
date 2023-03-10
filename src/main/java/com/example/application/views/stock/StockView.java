package com.example.application.views.stock;

import com.example.application.data.entity.SamplePerson;
import com.example.application.data.entity.StockItem;
import com.example.application.data.service.StockItemService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Stock")
@Route(value = "stock/:stockID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class StockView extends Div implements BeforeEnterObserver {

    private final String SAMPLEPERSON_ID = "stockItemId";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "stock/%s/edit";

    private final Grid<StockItem> grid = new Grid<>(StockItem.class, false);

    private SamplePerson samplePerson;

    private final StockItemService stockItemService;

    @Autowired
    public StockView(StockItemService stockItemService) {
        this.stockItemService = stockItemService;
        addClassNames("master-detail-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
//        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn(stockItem -> stockItem.getStore().getName())
                .setHeader("Store")
                .setAutoWidth(true);
        grid.addColumn(stockItem -> stockItem.getProduct().getName())
                .setHeader("Product")
                .setAutoWidth(true);
        grid.addColumn("amount")
                .setAutoWidth(true);

        grid.setItems(query -> stockItemService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
//        grid.asSingleSelect().addValueChangeListener(event -> {
//            if (event.getValue() != null) {
//                UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
//            } else {
//                clearForm();
//                UI.getCurrent().navigate(StockView.class);
//            }
//        });

//        // Configure Form
//        binder = new BeanValidationBinder<>(SamplePerson.class);
//
//        // Bind fields. This is where you'd define e.g. validation rules
//
//        binder.bindInstanceFields(this);
//
//        cancel.addClickListener(e -> {
//            clearForm();
//            refreshGrid();
//        });
//
//        save.addClickListener(e -> {
//            try {
//                if (this.samplePerson == null) {
//                    this.samplePerson = new SamplePerson();
//                }
//                binder.writeBean(this.samplePerson);
//                stockItemService.update(this.samplePerson);
//                clearForm();
//                refreshGrid();
//                Notification.show("SamplePerson details stored.");
//                UI.getCurrent().navigate(StockView.class);
//            } catch (ValidationException validationException) {
//                Notification.show("An exception happened while trying to store the samplePerson details.");
//            }
//        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
//        Optional<UUID> samplePersonId = event.getRouteParameters().get(SAMPLEPERSON_ID).map(UUID::fromString);
//        if (samplePersonId.isPresent()) {
//            Optional<SamplePerson> samplePersonFromBackend = stockItemService.get(samplePersonId.get());
//            if (samplePersonFromBackend.isPresent()) {
//                populateForm(samplePersonFromBackend.get());
//            } else {
//                Notification.show(
//                        String.format("The requested samplePerson was not found, ID = %s", samplePersonId.get()), 3000,
//                        Notification.Position.BOTTOM_START);
//                // when a row is selected but the data is no longer available,
//                // refresh grid
//                refreshGrid();
//                event.forwardTo(StockView.class);
//            }
//        }
    }

//    private void createEditorLayout(SplitLayout splitLayout) {
//        Div editorLayoutDiv = new Div();
//        editorLayoutDiv.setClassName("editor-layout");
//
//        Div editorDiv = new Div();
//        editorDiv.setClassName("editor");
//        editorLayoutDiv.add(editorDiv);
//
//        FormLayout formLayout = new FormLayout();
//        firstName = new TextField("First Name");
//        lastName = new TextField("Last Name");
//        email = new TextField("Email");
//        phone = new TextField("Phone");
//        dateOfBirth = new DatePicker("Date Of Birth");
//        occupation = new TextField("Occupation");
//        important = new Checkbox("Important");
//        formLayout.add(firstName, lastName, email, phone, dateOfBirth, occupation, important);
//
//        editorDiv.add(formLayout);
//        createButtonLayout(editorLayoutDiv);
//
//        splitLayout.addToSecondary(editorLayoutDiv);
//    }
//
//    private void createButtonLayout(Div editorLayoutDiv) {
//        HorizontalLayout buttonLayout = new HorizontalLayout();
//        buttonLayout.setClassName("button-layout");
//        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
//        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        buttonLayout.add(save, cancel);
//        editorLayoutDiv.add(buttonLayout);
//    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

//    private void clearForm() {
//        populateForm(null);
//    }
//
//    private void populateForm(SamplePerson value) {
//        this.samplePerson = value;
//        binder.readBean(this.samplePerson);
//
//    }
}
