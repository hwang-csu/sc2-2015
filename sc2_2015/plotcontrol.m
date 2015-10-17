function plotoverhead()
x1=[2 81];
colormap summer
bar([x1])
%xlabel('The number of endorsing sensors')
ylabel('Milliseconds')
%axis([0,0,0,120])
grid
set(gca, 'XTickLabel', {'Unrestrictions'; 'Restrictions'})
h = get(gca, 'xlabel');
set(h, 'FontSize', [16])
h = get(gca, 'ylabel');
set(h, 'FontSize', [16])
h = get(gca, 'title')
set(h, 'FontSize', [16])
set(gca, 'FontSize', [16])

