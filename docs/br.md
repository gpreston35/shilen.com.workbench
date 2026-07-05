## Update Bore Reamers page

# Goal
Update the Bore Reamer home page with additional search parameters.   
url: /tool/br

# Scope
- Add a field labeled "Caliber" to search form.
- Add fields "As Measured Low" and "As Measured High"
- "Caliber" will contain a list of calibers from operations.caliber where both bore_reamer_low and bore_reamer_high values are maintained and not 0.
-  If a "Caliber" is selected, the values of bore_reamer_low and bore_reamer_high will populate the values of "As Measured Low" and "As Measured High"
- Remove field "Flute Count", "Diameter Measured From", "Diameter Measured To" and "Style"
- Add an "Active" search field - "Yes" = 1, "No" = 0
- Arrange serach fields
- - row1: Active, Tool Identifier
- - row2: Caliber, As Measured Low, As Measured High
- Use populated serach fields to search against operations.tool_bore_reamer




